package com.capstone.eta.util.graph;
import java.util.*;
import org.javatuples.Pair;
import com.capstone.eta.util.weight.*;

abstract class GraphGenerator {
    // n, numer of nodes in graph
    int n;

    static Map<Pair<Integer, Integer>, Integer> graph = new HashMap<>();
    static Map<String, List<Pair<Integer, Integer>>> edgeNameToPairs = new HashMap<>();
    static Map<Pair<Integer, Integer>, String> pairToEdgeName = new HashMap<>();

    /**
     * @param Date date, current date in a Date class; String graphName, the name of the graph to be queried
     * @return Map<Pair<Integer, Integer>, Integer> graph, the graph with weight updated
     */
    public void generateGraph(Date date, String graphName) {
        for (Map.Entry<Pair<Integer, Integer>, Integer> entry : graph.entrySet()) {
            Pair<Integer, Integer> pair = entry.getKey();
            String queryEdgeName = pairToEdgeName.get(pair);
            GenerateWeightUtil generator = new GenerateWeightUtil();
            graph.put(pair, generator.getWeight(queryEdgeName, graphName, date));
        }
    }

    /**
     * Getter function for edgeNameToPairs
     * @return edgeNameToPairs
     */
    public Map<String, List<Pair<Integer, Integer>>> getEdgeNameToPairs() {
        return edgeNameToPairs;
    }

    /**
     * Getter function for pairToEdgeName
     * @return pairToEdgeName
     */
    public Map<Pair<Integer, Integer>, String> getPairToEdgeName() {
        return pairToEdgeName;
    }

    /**
     * @param String edgeName
     * @return Integer, the node that input edge points to
     */
    public Integer getEdgeNextNode(String edgeName) {
        return edgeNameToPairs.get(edgeName).get(0).getValue1();
    };

    /**
     * Getter function for graph
     * @return graph
     */
    public Map<Pair<Integer, Integer>, Integer> getGraph() {
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
