package com.capstone.eta.util.compute;
import java.util.*;
import org.javatuples.*;
import com.capstone.eta.util.graph.EGNetworkGraphGenerator;

public class EGNetworkCriticalPathGenerator extends CriticalPathGenerator {
    enum GraphName {
        EGNetwork,
        MoR,
        PreRack,
    }

    EGNetworkGraphGenerator egNetworkGraphGenerator = new EGNetworkGraphGenerator();

    public Pair<Pair<Integer, Integer>, Integer> compute(Date date) {
        egNetworkGraphGenerator.generateGraph(date, GraphName.EGNetwork.toString());
        Map<Pair<Integer, Integer>, Integer> graph = egNetworkGraphGenerator.getGraph();
        int n = egNetworkGraphGenerator.getN();
        return floydWarshall(graph, n);
    }

    public static void main(String[] args) {
        
    }

}
