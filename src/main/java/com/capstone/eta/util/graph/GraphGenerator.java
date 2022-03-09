package com.capstone.eta.util.graph;
import java.util.*;

import com.capstone.eta.entity.WorkOrder;
import com.capstone.eta.util.data.Milestone;

import org.javatuples.Pair;

abstract class GraphGenerator {
    // n, number of nodes in graph
    int n;
    String deliveryNumber;
    static Map<Pair<Integer, Integer>, Milestone> graph = new HashMap<>();
    static Map<String, List<Pair<Integer, Integer>>> edgeNameToPairs = new HashMap<>();
    List<WorkOrder> startedTasksEntities;
    /**
     * @param Date date, current date in a Date class;
     * @return Map<Pair<Integer, Integer>, Integer> graph, the graph with weight updated
     */
    public void updateGraph(Date curDate) {
        for (Map.Entry<Pair<Integer, Integer>, Milestone> entry : graph.entrySet()) {
            Pair<Integer, Integer> pair = entry.getKey();
            // long startTime = System.currentTimeMillis();
            graph.get(pair).updateMilestone(curDate, startedTasksEntities);
            // System.out.println("Update Time: " + (System.currentTimeMillis() - startTime));
        }
    }



    /**
     * Getter function for graph
     * @return graph
     */
    public Map<Pair<Integer, Integer>, Milestone> getGraph() {
        return graph;
    }

    /**
     * Getter function for n, number of nodes
     * @return n
     */
    public int getN() {
        return n;
    }
}
