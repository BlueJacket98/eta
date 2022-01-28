package com.capstone.eta.util.data;

import java.util.*;
import org.javatuples.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.capstone.eta.dao.DeliveryInfoRepository;
import com.capstone.eta.dao.WorkOrderRepository;
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
        EGNetwork,
        MoR,
        PreRack,
    }
    
    enum Status {
        NotStarted,
        InProgress,
        Finished,
    }

    private String milestoneName;
    private String graphName;
    private String DCCode;
    private Date startDate;
    private Task curTask;
    private Status status;
    private Integer milestoneWeight;
    private String deliveryNumber;
    private List<Pair<Task, Date>> finishedTasks = new ArrayList<>();
    private Map<String, Integer> slaMap;

    private CalendarService calendarService;

    private WorkOrderRepository workOrderRepository;

    private DeliveryInfoRepository deliveryInfoRepository;
    /**
     * Constructor
     * @param milestoneName
     * @param graphName
     * @param startDate
     */
    public Milestone(String deliveryNumber, String milestoneName, String graphName, Date startDate) {
        this.deliveryNumber = deliveryNumber;
        this.status = Status.NotStarted;
        this.milestoneName = milestoneName;
        this.graphName = graphName;
        this.startDate = startDate;     // Note startDate only meaningful when status != NotStarted
        // this.slaMap = JsonUtil.getTasksSLAMapFromGraphAndMilestoneName(graphName, milestoneName);
        this.calendarService = (CalendarService) ApplicationContextProvider.getBean("calendarService");
        this.workOrderRepository = (WorkOrderRepository) ApplicationContextProvider.getBean("workOrderRepository");
        this.deliveryInfoRepository = (DeliveryInfoRepository) ApplicationContextProvider.getBean("deliveryInfoRepository");
    }

    /**
     * Update the status, finishedTasks and milestoneWeight of the milestone
     * @param curDate
     * @return Integer, milestone weight
     */
    public void updateMilestone(Date curDate) {
        // update status and finished tasks
        if (deliveryInfoRepository.findByDeliveryNumberAndFpStartDateLessThanEqual(deliveryNumber, curDate).size() == 0) {
            status = Status.NotStarted;
        } else {
            List<WorkOrder> startedTasksEntities = workOrderRepository.findByDeliveryNumberAndStartDateLessThanEqual(deliveryNumber, curDate);
            List<Pair<Task, Date>> allFinishedTasks = new ArrayList<>();

            for (WorkOrder workOrder : startedTasksEntities) {
                if (workOrder.getEndDate().getTime() <= curDate.getTime()) {
                    allFinishedTasks.add(Pair.with(new Task(workOrder.getWorkOrderName()), curDate));
                }
            }
            
            finishedTasks = allFinishedTasks;
            // if the last work order name contains " - Ended"
            if (startedTasksEntities.size() == 0) {
                status = Status.NotStarted;
            }
            else if (startedTasksEntities.get(startedTasksEntities.size() - 1).getWorkOrderName().contains(" - Ended")) {
                status = Status.Finished;
            } else {
                status = Status.InProgress;
            }
        }

        // calculate weight
        // if not started, use model trained with full dataset
        if (this.status == Status.NotStarted) {
            this.milestoneWeight = WeightGenerator.getModelWeight(milestoneName, graphName, curDate);
        // if already finished, use the actual duration
        } else if (this.status == Status.Finished) {
            this.milestoneWeight = WeightGenerator.getHistWeight(milestoneName, graphName, curDate);
        // if milestone status is InProgress
        } else {
            Integer modelEstimatedTime = WeightGenerator.getModelWeight(milestoneName, graphName, curDate);

            float actualCompletionRate = getActualCompletionRate(curDate, modelEstimatedTime);
            float standardCompletionRate = getStandardCompletionRate();

            Integer actualTimeForFinishedTasks = getActualTimeForFinishedTasks();
            Integer standardTimeForFinishedTasks = getStandardTimeForFinishedTasks();

            if (actualCompletionRate >= standardCompletionRate) {
                this.milestoneWeight = modelEstimatedTime + (actualTimeForFinishedTasks - standardTimeForFinishedTasks);
            } else {
                this.milestoneWeight = modelEstimatedTime - (standardTimeForFinishedTasks - actualTimeForFinishedTasks);
            }
        }
    }

    /**
     * Get the SLA for the milestone
     * @return Integer
     */
    private Integer getStandardTimeForMilestone() {
        return JsonUtil.getMilestoneSLAFromGraphAndMilestoneName(graphName, milestoneName);
    }


    /**
     * Get the difference between milestone start date and last finished task finish date
     * @return Integer
     */
    private Integer getActualTimeForFinishedTasks() {
        return DateUtil.dateDiffInDaysAbs(startDate, finishedTasks.get(finishedTasks.size() - 1).getValue1());
    }


    /**
     * Get the total SLA for finished tasks
     * @return Integer
     */
    private Integer getStandardTimeForFinishedTasks() {
        Integer totalSLA = (int)0;
        for (Pair<Task, Date> finishedTask : finishedTasks) {
            // totalSLA += slaMap.get(finishedTask.getValue0().getTaskName());
            totalSLA += 1;
        }
        return totalSLA;
    }

    /**
     * Get Actual Completion Rate according to:
     *  The actual completion rate = (time already took - holiday & lockdown days) 
     *                              / the predicted net time the milestone will take
     * @param curDate
     * @param modelEstimatedTime
     * @return
     */
    private float getActualCompletionRate(Date curDate, Integer modelEstimatedTime) {
        Integer actualTimeTook = DateUtil.dateDiffInDaysAbs(curDate, startDate);
        Integer lockdownAndHolidaysDays = calendarService.getLockdownAndHolidaysBetween(DCCode, curDate, startDate);
        return (float)(actualTimeTook - lockdownAndHolidaysDays) / modelEstimatedTime;
    }

    /**
     * Get Standard Completion Rate according to:
     *  The standard completion rate = the sum of net SLA of currently completed tasks 
     *                                  / the net SLA of the milestone
     * @return
     */
    private float getStandardCompletionRate() {
        Integer standardTimeForFinishedTasks = getStandardTimeForFinishedTasks();
        Integer standardTimeForMilestone = getStandardTimeForMilestone();
        return (float)(standardTimeForFinishedTasks / standardTimeForMilestone);
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
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
