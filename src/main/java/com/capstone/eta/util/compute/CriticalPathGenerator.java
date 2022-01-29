package com.capstone.eta.util.compute;
import java.util.*;

import com.capstone.eta.util.data.Milestone;

import org.javatuples.*;

abstract public class CriticalPathGenerator {
    Map<Pair<Integer, Integer>, Integer> graph;
    int n;

    /**
     * Use Floyd-Warshall Algorithm to find the longest weighted path in the graph
     * @param graph
     * @param n
     * @return Pair<Pair<Start, End>, maxTotalWeight>
     */
    public Pair<Pair<Integer, Integer>, Integer> floydWarshall(Map<Pair<Integer, Integer>, Milestone> inputGraph, int n) {
        int maxTotalWeight = 0;
        
        // preprocessing of the graph, make value into integer -> weight
        Integer[][] f = new Integer [n][n];
        // Map<Pair<Integer, Integer>, Integer> graph = new HashMap<>();
        // for (Map.Entry<Pair<Integer, Integer>, Milestone> entry : inputGraph.entrySet()) {
        //     graph.put(entry.getKey(), -entry.getValue().getMilestoneWeight());
        // }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (inputGraph.containsKey(Pair.with((Integer)i, (Integer)j))) {
                    f[i][j] = -inputGraph.get(Pair.with((Integer)i, (Integer)j)).getMilestoneWeight();
                } else if (i == j) {
                    f[i][j] = 0;
                } else {
                    f[i][j] = 10000;
                }
            }
        }

        System.out.println("Graph: " + Arrays.deepToString(f));

        // main body of floyd-warshall algorithm
        Pair<Integer, Integer> criticalPath = Pair.with(0, 0);
        for (int k = 0; k < n; k++) {
            for (int x = 0; x < n; x++) {
                for (int y = 0; y < n; y++) {
                    // graph.put(Pair.with(x, y), 
                    //           Math.min(graph.getOrDefault(Pair.with(x, y), 0), 
                    //                    graph.getOrDefault(Pair.with(x, k), 0) + graph.getOrDefault(Pair.with(k, y), 0)));
                    
                    // if (graph.get(Pair.with(x, y)) < maxTotalWeight) {
                    //     maxTotalWeight = graph.get(Pair.with(x, y));
                    //     criticalPath = Pair.with(x, y);
                    f[x][y] = Math.min(f[x][y], f[x][k] + f[k][y]);
                    // System.out.println("f[x][y]" + f[x][y] + " " + "f[x][k] + f[k][y]" + (f[x][k] + f[k][y]));
                    if (f[x][y] < maxTotalWeight) {
                        maxTotalWeight = f[x][y];
                        criticalPath = Pair.with(x, y);
                    
                    }
                }
            }
        }
        return Pair.with(criticalPath, -maxTotalWeight);
    }

    abstract public Pair<Pair<Integer, Integer>, Integer> compute(Date date);

    public static void main(String[] args) {
        
    }
}
