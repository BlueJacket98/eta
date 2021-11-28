package com.capstone.eta.util.compute;
import java.util.*;
import org.javatuples.*;

import com.capstone.eta.util.data.Milestone;
import com.capstone.eta.util.graph.EGNetworkGraphGenerator;

public class EGNetworkCriticalPathGenerator extends CriticalPathGenerator {
    enum GraphName {
        EGNetwork,
        MoR,
        PreRack,
    }

    EGNetworkGraphGenerator egNetworkGraphGenerator;

    public EGNetworkCriticalPathGenerator(String deliveryNumber) {
        egNetworkGraphGenerator = new EGNetworkGraphGenerator(deliveryNumber);
    }
    
    
    public Pair<Pair<Integer, Integer>, Integer> compute(Date date) {
        egNetworkGraphGenerator.updateGraph(date);
        Map<Pair<Integer, Integer>, Milestone> graph = egNetworkGraphGenerator.getGraph();
        int n = egNetworkGraphGenerator.getN();
        return floydWarshall(graph, n);
    }

    public static void main(String[] args) {
        
    }

}
