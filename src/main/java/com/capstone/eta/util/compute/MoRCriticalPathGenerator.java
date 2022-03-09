package com.capstone.eta.util.compute;
import java.util.*;
import org.javatuples.*;

import com.capstone.eta.entity.WorkOrder;
import com.capstone.eta.util.data.Milestone;
import com.capstone.eta.util.graph.MoRGraphGenerator;

public class MoRCriticalPathGenerator extends CriticalPathGenerator {
    enum GraphName {
        EngineeringGroupNetwork,
        Mor,
        PreRack,
        PreBuiltRow,
    }

    MoRGraphGenerator moRGraphGenerator;
    
    public MoRCriticalPathGenerator(String deliveryNumber, List<WorkOrder> startedTasksEntities) {
        moRGraphGenerator = new MoRGraphGenerator(deliveryNumber, startedTasksEntities);
    }

    public Pair<Pair<Integer, Integer>, Integer> compute(Date date) {
        moRGraphGenerator.updateGraph(date);
        Map<Pair<Integer, Integer>, Milestone> graph = moRGraphGenerator.getGraph();
        int n = moRGraphGenerator.getN();
        return floydWarshall(graph, n);
    }

    public static void main(String[] args) {
        
    }
}
