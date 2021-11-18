package com.capstone.eta.util.compute;
import java.util.*;
import org.javatuples.*;

public class CriticalPathGenerator {
    // // MaxHeap, sort by the total weight
    // // Store all possible path from current milestone to end
    // PriorityQueue<Pair<List<Integer>, Integer>> heap = new PriorityQueue<>(
    //     new Comparator<Pair<List<Integer>, Integer>>() {
    //         @Override
    //         public int compare(Pair<List<Integer>, Integer> path1, Pair<List<Integer>, Integer> path2) {
    //             return path1.getValue1() > path2.getValue1() ? -1 : 1;
    //         }
    //     }
    // );

    // public Pair<List<Integer>, Integer> computeMilestones(List<String> curEdges, Map<Pair<Integer, Integer>, Integer> graph,
    //                                                         Map<String, List<Pair<Integer, Integer>>> edgeNameToPairs, int n) {
    //     for (String edge : curEdges) {
    //         computeOneMilestone(edge, graph, edgeNameToPairs);
    //     }
    //     return heap.peek();
    // }
    Map<Pair<Integer, Integer>, Integer> graph;
    int n;

    /**
     * Use Floyd-Warshall Algorithm to find the longest weighted path in the graph
     * @param graph
     * @param n
     * @return Pair<Pair<Start, End>, maxTotalWeight>
     */
    public Pair<Pair<Integer, Integer>, Integer> floydWarshall(Map<Pair<Integer, Integer>, Integer> graph, int n) {
        int maxTotalWeight = 0;
        Pair<Integer, Integer> criticalPath = Pair.with(0, 0);
        for (int k = 1; k <= n; k++) {
            for (int x = 1; x <= n; x++) {
                for (int y = 1; y <= n; y++) {
                    graph.put(Pair.with(x, y), 
                            Math.max(graph.get(Pair.with(x, y)), graph.get(Pair.with(x, k)) + graph.get(Pair.with(k, y))));
                    if (graph.get(Pair.with(x, y)) > maxTotalWeight) {
                        maxTotalWeight = graph.get(Pair.with(x, y));
                        criticalPath = Pair.with(x, y);
                    }
                }
            }
        }
        return Pair.with(criticalPath, maxTotalWeight);
    }

    public static void main(String[] args) {
        
    }
}
