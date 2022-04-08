package com.capstone.eta.util.compute;

import com.capstone.eta.entity.WorkOrder;
import com.capstone.eta.util.data.Milestone;
import com.capstone.eta.util.graph.PreBuiltRowGraphGenerator;
import org.javatuples.*;
import java.util.*;

public class PreBuiltRowCriticalPathGenerator extends CriticalPathGenerator {
    enum GraphName {
        EngineeringGroupNetwork,
        Mor,
        PreRack,
        PreBuiltRow,
    }

    PreBuiltRowGraphGenerator preBuiltRowGraphGenerator;
    
    public PreBuiltRowCriticalPathGenerator(String deliveryNumber, List<WorkOrder> startedTasksEntities) {
        preBuiltRowGraphGenerator = new PreBuiltRowGraphGenerator(deliveryNumber, startedTasksEntities);
    }

    public Pair<Pair<Integer, Integer>, Integer> compute(Date date) {
        preBuiltRowGraphGenerator.updateGraph(date);
        Map<Pair<Integer, Integer>, Milestone> graph = preBuiltRowGraphGenerator.getGraph();
        int n = preBuiltRowGraphGenerator.getN();
        System.out.println("PreBuiltRow");
        return floydWarshall(graph, n);
    }

    public static void main(String[] args) {
        
    }  

}
