package com.capstone.eta.util.compute;
import java.util.*;
import org.javatuples.*;
import com.capstone.eta.util.graph.MoRGraphGenerator;

public class MoRCriticalPathGenerator extends CriticalPathGenerator {
    enum GraphName {
        EGNetwork,
        MoR,
        PreRack,
    }

    MoRGraphGenerator moRGraphGenerator = new MoRGraphGenerator();

    public Pair<Pair<Integer, Integer>, Integer> compute(Date date) {
        moRGraphGenerator.generateGraph(date, GraphName.MoR.toString());
        Map<Pair<Integer, Integer>, Integer> graph = moRGraphGenerator.getGraph();
        int n = moRGraphGenerator.getN();
        return floydWarshall(graph, n);
    }

    public static void main(String[] args) {
        
    }
}
