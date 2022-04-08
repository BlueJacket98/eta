package com.capstone.eta.util.data;

import java.util.*;
import org.javatuples.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.eta.dao.AvgDaysFromStartRepository;
import com.capstone.eta.dao.DeliveryInfoRepository;
import com.capstone.eta.dao.SLARepository;
import com.capstone.eta.dao.WorkOrderRepository;
import com.capstone.eta.entity.AvgDaysFromStart;
import com.capstone.eta.entity.DeliveryInfo;
import com.capstone.eta.entity.SLA;
import com.capstone.eta.entity.WorkOrder;
import com.capstone.eta.service.CalendarService;
import com.capstone.eta.util.date.DateUtil;
import com.capstone.eta.util.json.JsonUtil;
import com.capstone.eta.util.spring.ApplicationContextProvider;
import com.capstone.eta.util.weight.WeightGenerator;

import lombok.Data;

@Data
public class Milestone {
    enum GraphName {
        EngineeringGroupNetwork,
        Mor,
        PreRack,
    }
    
    enum Status {
        NotStarted,
        InProgress,
        Finished,
    }

    private String milestoneName;
    private String graphName;
    private Task curTask;
    private Status status;
    private Integer milestoneWeight;
    private String deliveryNumber;
    private Float sla;
    private WeightGenerator weightGenerator;
    private String deploymentSeverity;

    private SLARepository slaRepository;
    private DeliveryInfoRepository deliveryInfoRepository;
    private AvgDaysFromStartRepository avgDaysFromStartRepository;
    /**
     * Constructor
     * @param milestoneName
     * @param graphName
     * @param startDate
     */
    public Milestone(String deliveryNumber, String milestoneName, String graphName) {
        this.slaRepository = (SLARepository) ApplicationContextProvider.getBean("slaRepository");
        this.deliveryInfoRepository = (DeliveryInfoRepository) ApplicationContextProvider.getBean("deliveryInfoRepository");
        this.avgDaysFromStartRepository = (AvgDaysFromStartRepository) ApplicationContextProvider.getBean("avgDaysFromStartRepository");

        this.deliveryNumber = deliveryNumber;
        this.status = Status.NotStarted;
        this.milestoneName = milestoneName;
        this.graphName = graphName;
        // this.startDate = startDate;     // Note startDate only meaningful when status != NotStarted
        // this.slaMap = JsonUtil.getTasksSLAMapFromGraphAndMilestoneName(graphName, milestoneName);
        this.weightGenerator = WeightGenerator.getInstance();
        DeliveryInfo deliveryInfo = deliveryInfoRepository.findByDeliveryNumber(deliveryNumber).get(0);
        this.deploymentSeverity = deliveryInfo.getDeploymentSeverity();
        List<SLA> slaList = slaRepository.findByTaskGroupTypeAndMilestoneNameAndRegionAndDcCodeAndDeploymentSeverity(
            graphName, milestoneName, deliveryInfo.getRegion(), deliveryInfo.getDcCode(), deploymentSeverity);
        if (slaList.size() == 0) {
            this.sla = (float)10;
        } else {
            this.sla = slaList.get(0).getSla();
        }
    }

    /**
     * Update the status, finishedTasks and milestoneWeight of the milestone
     * @param curDate
     * @return void
     */
    public void updateMilestone(Date curDate, List<WorkOrder> startedTasksEntities) {
        List<WorkOrder> startedTasksEntitiesInMilestone = new ArrayList<>();
        for (WorkOrder startedTaskEntity: startedTasksEntities) {
            if (startedTaskEntity.getMilestoneName() == milestoneName) {
                startedTasksEntitiesInMilestone.add(startedTaskEntity);
            }
        }
        // Status prevStatus = status;

        // update status and finished tasks
        if (status == Status.Finished) {
            ;
        } else if (startedTasksEntitiesInMilestone.size() == 0) {
            status = Status.NotStarted;
        } else if (startedTasksEntitiesInMilestone.get(startedTasksEntitiesInMilestone.size() - 1).getWorkOrderName().contains(" - Ended")) {
            status = Status.Finished;
        } else {
            status = Status.InProgress;
        }
        

        // calculate weight
        // if not started, use model trained with full dataset
        if (this.status == Status.NotStarted) {
            // if (prevStatus != this.status) {
            long startTime1 = System.currentTimeMillis();
            this.milestoneWeight = weightGenerator.getModelWeight(deliveryNumber, milestoneName, graphName, curDate, sla);
            // System.out.println("Not started get weight time: " + (System.currentTimeMillis() - startTime1));
            // }
        } else if (this.status == Status.Finished) {
            this.milestoneWeight = DateUtil.dateDiffInDaysAbs(
                startedTasksEntitiesInMilestone.get(startedTasksEntitiesInMilestone.size() - 1).getEndDate(), 
                startedTasksEntitiesInMilestone.get(0).getStartDate());
        // if milestone status is InProgress, add the diff between used and avg
        } else {
            long startTime1 = System.currentTimeMillis();
            this.milestoneWeight = weightGenerator.getModelWeight(deliveryNumber, milestoneName, graphName, curDate, sla);
            // System.out.println("In progress get weight time: " + (System.currentTimeMillis() - startTime1));

            WorkOrder firstWorkOrder = startedTasksEntitiesInMilestone.get(0);
            WorkOrder lastWorkOrder = startedTasksEntitiesInMilestone.get(startedTasksEntitiesInMilestone.size() - 1);
            Integer usedDays = DateUtil.dateDiffInDaysAbs(curDate, firstWorkOrder.getStartDate());

            long startTime2 = System.currentTimeMillis();
            List<AvgDaysFromStart> histRecords = avgDaysFromStartRepository.findByTaskGroupTypeAndMilestoneNameAndWorkOrderNameAndDeploymentSeverity
                        (graphName, milestoneName, lastWorkOrder.getWorkOrderName(), deploymentSeverity);
            // System.out.println("Find hist time: " + (System.currentTimeMillis() - startTime2));
            
            Integer avgDays = usedDays;
            if (histRecords.size() != 0) {
                avgDays = Math.round(histRecords.get(0).getAvgDays());
            }
            this.milestoneWeight += (usedDays - avgDays);
        }
    }



    public String getMilestoneName() {
        return milestoneName;
    }

    public void setMilestoneName(String milestoneName) {
        this.milestoneName = milestoneName;
    }

    public String getGraphName() {
        return graphName;
    }

    public void setGraphName(String graphName) {
        this.graphName = graphName;
    }


    public Task getCurTask() {
        return curTask;
    }

    public void setCurTask(Task curTask) {
        this.curTask = curTask;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getMilestoneWeight() {
        return milestoneWeight;
    }

    public void setMilestoneWeight(Integer milestoneWeight) {
        this.milestoneWeight = milestoneWeight;
    }

    
    
}
