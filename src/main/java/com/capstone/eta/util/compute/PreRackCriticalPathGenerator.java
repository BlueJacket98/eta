package com.capstone.eta.util.compute;
import java.util.*;
import org.javatuples.*;
import com.capstone.eta.util.graph.PreRackGraphGenerator;

public class PreRackCriticalPathGenerator extends CriticalPathGenerator {
    enum GraphName {
        EGNetwork,
        MoR,
        PreRack,
    }

    PreRackGraphGenerator preRackGraphGenerator = new PreRackGraphGenerator();

    public Pair<Pair<Integer, Integer>, Integer> compute(Date date) {
        preRackGraphGenerator.generateGraph(date, GraphName.MoR.toString());
        Map<Pair<Integer, Integer>, Integer> graph = preRackGraphGenerator.getGraph();
        int n = preRackGraphGenerator.getN();
        return floydWarshall(graph, n);
    }

    public static void main(String[] args) {
        
    }    
}
