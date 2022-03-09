package com.capstone.eta.util.compute;
import java.util.*;
import org.javatuples.*;

import com.capstone.eta.entity.WorkOrder;
import com.capstone.eta.util.data.Milestone;
import com.capstone.eta.util.graph.PreRackGraphGenerator;

public class PreRackCriticalPathGenerator extends CriticalPathGenerator {
    enum GraphName {
        EngineeringGroupNetwork,
        Mor,
        PreRack,
        PreBuiltRow,
    }

    PreRackGraphGenerator preRackGraphGenerator;

    public PreRackCriticalPathGenerator(String deliveryNumber, List<WorkOrder> startedTasksEntities) {
        preRackGraphGenerator = new PreRackGraphGenerator(deliveryNumber, startedTasksEntities);
    }

    public Pair<Pair<Integer, Integer>, Integer> compute(Date date) {
        preRackGraphGenerator.updateGraph(date);
        Map<Pair<Integer, Integer>, Milestone> graph = preRackGraphGenerator.getGraph();
        int n = preRackGraphGenerator.getN();
        return floydWarshall(graph, n);
    }

    public static void main(String[] args) {
        
    }    
}
