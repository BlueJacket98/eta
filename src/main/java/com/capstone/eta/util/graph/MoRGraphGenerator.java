/**
 * A util class that generates the graph on a certain date.
 * Call generateGraph() function to generate the graph on a specific date
 * Call main() function to test
 */
package com.capstone.eta.util.graph;
import java.util.*;
import org.javatuples.*;

public class MoRGraphGenerator extends GraphGenerator {
    enum GraphName {
        EGNetwork,
        MoR,
        PreRack,
    }

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

    /**
     * Constructor, initialize the graph structure with weight set to 0
     */
    public MoRGraphGenerator() {
        graph.put(Pair.with(0, 1), 0);
        graph.put(Pair.with(1, 2), 0);
        graph.put(Pair.with(2, 11), 0);
        graph.put(Pair.with(3, 4), 0);
        graph.put(Pair.with(4, 5), 0);
        graph.put(Pair.with(5, 11), 0);
        graph.put(Pair.with(4, 10), 0);
        graph.put(Pair.with(6, 7), 0);
        graph.put(Pair.with(7, 11), 0);
        graph.put(Pair.with(8, 9), 0);
        graph.put(Pair.with(9, 11), 0);

        pairToEdgeName.put(Pair.with(0, 1), EdgeName.ProcureAndReceiveNetworkEquipment.toString());
        pairToEdgeName.put(Pair.with(1, 2), EdgeName.HardwareInstallationAndValidation.toString());
        pairToEdgeName.put(Pair.with(2, 11), EdgeName.CableAndConfiguration.toString());
        pairToEdgeName.put(Pair.with(3, 4), EdgeName.ArtifactGeneration.toString());
        pairToEdgeName.put(Pair.with(4, 5), EdgeName.GenerateCableMaps.toString());
        pairToEdgeName.put(Pair.with(5, 11), EdgeName.CableAndConfiguration.toString());
        pairToEdgeName.put(Pair.with(4, 10), EdgeName.UpstreamDeviceConfig.toString());
        pairToEdgeName.put(Pair.with(6, 7), EdgeName.ScheduleResources.toString());
        pairToEdgeName.put(Pair.with(7, 11), EdgeName.CableAndConfiguration.toString());
        pairToEdgeName.put(Pair.with(8, 9), EdgeName.ProcureAndReceiveCables.toString());
        pairToEdgeName.put(Pair.with(9, 11), EdgeName.CableAndConfiguration.toString());
    }


    /**
     * Testing function
     * @param args
     */
    public static void main(String[] args) {

    }    
}
