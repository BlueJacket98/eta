package com.capstone.eta.service;
import java.util.*;
import org.javatuples.Pair;
import org.springframework.stereotype.Service;

import com.capstone.eta.util.compute.EGNetworkCriticalPathGenerator;
import com.capstone.eta.util.compute.MoRCriticalPathGenerator;
import com.capstone.eta.util.compute.PreRackCriticalPathGenerator;
import com.capstone.eta.util.date.*;
import com.capstone.eta.util.graph.EGNetworkGraphGenerator;
import com.capstone.eta.util.graph.MoRGraphGenerator;
import com.capstone.eta.util.graph.PreRackGraphGenerator;

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

    /**
     * Initialize estimation service with deliveryId
     * @param deliveryId
     */
    public void initEstimationService(String deliveryId) {
        egNetworkCriticalPathGenerator = new EGNetworkCriticalPathGenerator(deliveryId);
        moRCriticalPathGenerator = new MoRCriticalPathGenerator(deliveryId);
        preRackCriticalPathGenerator = new PreRackCriticalPathGenerator(deliveryId);
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
        Integer estimatedDuration = Math.max(egNetworkRes.getValue1(), Math.max(moRRes.getValue1(), preRackRes.getValue1())); 
        System.out.println(estimatedDuration);
        return estimatedDuration;
    }

    public static void main(String[] args) {
        
    }
}

