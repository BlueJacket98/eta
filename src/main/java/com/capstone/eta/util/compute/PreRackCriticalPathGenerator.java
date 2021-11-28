package com.capstone.eta.util.compute;
import java.util.*;
import org.javatuples.*;

import com.capstone.eta.util.data.Milestone;
import com.capstone.eta.util.graph.PreRackGraphGenerator;

public class PreRackCriticalPathGenerator extends CriticalPathGenerator {
    enum GraphName {
        EGNetwork,
        MoR,
        PreRack,
    }

    PreRackGraphGenerator preRackGraphGenerator;

    public PreRackCriticalPathGenerator(String deliveryNumber) {
        preRackGraphGenerator = new PreRackGraphGenerator(deliveryNumber);
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
