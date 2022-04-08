package com.capstone.eta.util.compute;
import java.util.*;
import org.javatuples.*;

import com.capstone.eta.entity.WorkOrder;
import com.capstone.eta.util.data.Milestone;
import com.capstone.eta.util.graph.EGNetworkGraphGenerator;

public class EGNetworkCriticalPathGenerator extends CriticalPathGenerator {
    enum GraphName {
        EngineeringGroupNetwork,
        Mor,
        PreRack,
        PreBuiltRow,
    }

    EGNetworkGraphGenerator egNetworkGraphGenerator;

    public EGNetworkCriticalPathGenerator(String deliveryNumber, List<WorkOrder> startedTasksEntities) {
        egNetworkGraphGenerator = new EGNetworkGraphGenerator(deliveryNumber, startedTasksEntities);
        this.startedTasksEntities = startedTasksEntities;
    }
    
    
    public Pair<Pair<Integer, Integer>, Integer> compute(Date date) {
        egNetworkGraphGenerator.updateGraph(date);
        Map<Pair<Integer, Integer>, Milestone> graph = egNetworkGraphGenerator.getGraph();
        int n = egNetworkGraphGenerator.getN();
        System.out.println("EngineeringGroupNetwork");
        return floydWarshall(graph, n);
    }

    public static void main(String[] args) {
        
    }

}
