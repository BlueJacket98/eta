package com.capstone.eta.service;
import java.util.*;
import org.javatuples.Pair;
import com.capstone.eta.util.compute.EGNetworkCriticalPathGenerator;
import com.capstone.eta.util.compute.MoRCriticalPathGenerator;
import com.capstone.eta.util.compute.PreRackCriticalPathGenerator;
import com.capstone.eta.util.date.*;
import com.capstone.eta.util.graph.EGNetworkGraphGenerator;
import com.capstone.eta.util.graph.MoRGraphGenerator;
import com.capstone.eta.util.graph.PreRackGraphGenerator;

import org.apache.catalina.Engine;

public class ETAService {
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


    ETAService() {
        egNetworkCriticalPathGenerator = new EGNetworkCriticalPathGenerator();
        moRCriticalPathGenerator = new MoRCriticalPathGenerator();
        preRackCriticalPathGenerator = new PreRackCriticalPathGenerator();
    }

    public void elapse() {
        Pair<Pair<Integer, Integer>, Integer> egNetworkRes = egNetworkCriticalPathGenerator.compute(curDate);
        Pair<Pair<Integer, Integer>, Integer> moRRes = moRCriticalPathGenerator.compute(curDate);
        Pair<Pair<Integer, Integer>, Integer> preRackRes = preRackCriticalPathGenerator.compute(curDate);
        Integer estimatedDuration = Math.max(egNetworkRes.getValue1(), Math.max(moRRes.getValue1(), preRackRes.getValue1())) 
        System.out.println(estimatedDuration);

        curDate = DateAddOne.dateAddOne(curDate);
    }

    public static void main(String[] args) {
        
    }
}

