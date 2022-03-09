package com.capstone.eta.service;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javatuples.Pair;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.capstone.eta.entity.WorkOrder;
import com.capstone.eta.util.compute.EGNetworkCriticalPathGenerator;
import com.capstone.eta.util.compute.MoRCriticalPathGenerator;
import com.capstone.eta.util.compute.PreBuiltRowCriticalPathGenerator;
import com.capstone.eta.util.compute.PreRackCriticalPathGenerator;

@Service
public class EstimationService {
    enum GraphName {
        EGNetwork,
        MoR,
        PreRack,
    }

    Date curDate;
    Date startDate;

    EGNetworkCriticalPathGenerator egNetworkCriticalPathGenerator;
    MoRCriticalPathGenerator moRCriticalPathGenerator;
    PreRackCriticalPathGenerator preRackCriticalPathGenerator;
    PreBuiltRowCriticalPathGenerator preBuiltRowCriticalPathGenerator;
    /**
     * Initialize estimation service with deliveryId
     * @param deliveryId
     */
    public void initEstimationService(String deliveryId, List<WorkOrder> startedTasksEntities) {
        egNetworkCriticalPathGenerator = new EGNetworkCriticalPathGenerator(deliveryId, startedTasksEntities);
        moRCriticalPathGenerator = new MoRCriticalPathGenerator(deliveryId, startedTasksEntities);
        preRackCriticalPathGenerator = new PreRackCriticalPathGenerator(deliveryId, startedTasksEntities);
        preBuiltRowCriticalPathGenerator = new PreBuiltRowCriticalPathGenerator(deliveryId, startedTasksEntities);
    }

    /**
     * Get the estimated duration for a certain delivery
     * @param curDate
     * @return
     */
    public Integer getEstimatedDuration(Date curDate, String deliveryId) {
        Pair<Pair<Integer, Integer>, Integer> egNetworkRes = egNetworkCriticalPathGenerator.compute(curDate);
        Pair<Pair<Integer, Integer>, Integer> moRRes = moRCriticalPathGenerator.compute(curDate);
        Pair<Pair<Integer, Integer>, Integer> preRackRes = preRackCriticalPathGenerator.compute(curDate);
        Pair<Pair<Integer, Integer>, Integer> preBuiltRowRes = preBuiltRowCriticalPathGenerator.compute(curDate);
        Integer estimatedDuration = Math.max(preBuiltRowRes.getValue1(), 
                                        Math.max(egNetworkRes.getValue1(), 
                                            Math.max(moRRes.getValue1(), preRackRes.getValue1()))); 
        System.out.println(estimatedDuration);
        System.out.println("egNetworkRes: " + egNetworkRes);
        System.out.println("moRRes: " + moRRes);
        System.out.println("preRackRes: " + preRackRes);
        System.out.println("preBuiltRowRes: " + preBuiltRowRes);
        return estimatedDuration;
    }

    public static void main(String[] args) {
        
    }
}

