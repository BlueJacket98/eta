/**
 * A util class that generates the graph on a certain date.
 * Call generateGraph() function to generate the graph on a specific date
 * Call main() function to test
 * @author Zhizhong Wu
 */
package com.capstone.eta.util;
import java.util.*;
import org.javatuples.*;

public class GenerateMoRGraphUtil {
    enum EdgeName {
        ProcureAndReceiveNetworkEquipment,
        HardwareInstallationAndValidation,
        CableAndConfiguration,
        ArtifactGeneration,
        GenerateCableMaps,
        UpstreamDeviceConfig,
        ScheduleResources,
        ProcureAndReceiveCables,
    }

    static Map<Pair<Integer, Integer>, Integer> graph = new HashMap<>();
    static Map<String, List<Pair<Integer, Integer>>> edgeNameToPairs = new HashMap<>();
    static Map<Pair<Integer, Integer>, String> pairToEdgeName = new HashMap<>();

    /**
     * Constructor, initialize the graph structure with weight set to 0
     */
    GenerateMoRGraphUtil() {
        graph.put(Pair.with(0, 1), 0);
        graph.put(Pair.with(1, 2), 0);
        graph.put(Pair.with(2, 7), 0);
        graph.put(Pair.with(3, 4), 0);
        graph.put(Pair.with(4, 5), 0);
        graph.put(Pair.with(4, 6), 0);
        graph.put(Pair.with(5, 7), 0);

        edgeNameToPairs.put(EdgeName.ProcureAndReceiveNetworkEquipment.toString(), Arrays.asList(Pair.with(0, 1)));
        edgeNameToPairs.put(EdgeName.HardwareInstallationAndValidation.toString(), Arrays.asList(Pair.with(1, 2)));
        edgeNameToPairs.put(EdgeName.CableAndConfiguration.toString(), Arrays.asList(Pair.with(2, 7), Pair.with(5, 7)));
        edgeNameToPairs.put(EdgeName.ArtifactGeneration.toString(), Arrays.asList(Pair.with(3, 4)));
        edgeNameToPairs.put(EdgeName.GenerateCableMaps.toString(), Arrays.asList(Pair.with(4, 5)));
        edgeNameToPairs.put(EdgeName.UpstreamDeviceConfig.toString(), Arrays.asList(Pair.with(4, 6)));

        pairToEdgeName.put(Pair.with(0, 1), EdgeName.ProcureAndReceiveNetworkEquipment.toString());
        pairToEdgeName.put(Pair.with(1, 2), EdgeName.HardwareInstallationAndValidation.toString());
        pairToEdgeName.put(Pair.with(2, 7), EdgeName.CableAndConfiguration.toString());
        pairToEdgeName.put(Pair.with(5, 7), EdgeName.CableAndConfiguration.toString());
        pairToEdgeName.put(Pair.with(3, 4), EdgeName.ArtifactGeneration.toString());
        pairToEdgeName.put(Pair.with(4, 5), EdgeName.GenerateCableMaps.toString());
        pairToEdgeName.put(Pair.with(4, 6), EdgeName.UpstreamDeviceConfig.toString());
    }

    /**
     * @param Date date, current date in a Date class
     * @return Map<Pair<Integer, Integer>, Integer> graph, the graph with weight updated
     */
    public static Map<Pair<Integer, Integer>, Integer> generateGraph(Date date) {
        for (Map.Entry<Pair<Integer, Integer>, Integer> entry : graph.entrySet()) {
            Pair<Integer, Integer> pair = entry.getKey();
            String queryEdgeName = pairToEdgeName.get(pair);
            GenerateGraphUtil generator = new GenerateGraphUtil();
            graph.put(pair, generator.getWeight(queryEdgeName, "EGNetwork", date));
        }
        return graph;
    }

    /**
     * Testing function
     * @param args
     */
    public static void main(String[] args) {

    }    
}
