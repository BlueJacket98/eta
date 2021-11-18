/**
 * A util class that generates the graph on a certain date.
 * Call generateGraph() function to generate the graph on a specific date
 * Call main() function to test
 * @author Zhizhong Wu
 */
package com.capstone.eta.util.graph;
import java.util.*;
import org.javatuples.*;

public class PreRackGraphGenerator extends GraphGenerator {
    enum GraphName {
        EGNetwork,
        MoR,
        PreRack,
    }

    enum EdgeName {
        Reservation,
        ScheduleResources,
        ProcureAndReceivePowerWhips,
        ProcureAndRecieveInfraMaterial,
        InstallInfra,
        ProcureAndReceiveCables,
        PreCablePRD,
        ArtifactGeneration,
        GenerateCableMaps,
        UpstreamDeviceConfig,
    }

    /**
     * Constructor, initialize the graph structure with weight set to 0
     */
    public PreRackGraphGenerator() {
        n = 10;

        graph.put(Pair.with(0, 1), 0);
        graph.put(Pair.with(1, 2), 0);
        graph.put(Pair.with(1, 3), 0);
        graph.put(Pair.with(1, 4), 0);
        graph.put(Pair.with(4, 5), 0);
        graph.put(Pair.with(1, 6), 0);
        graph.put(Pair.with(6, 7), 0);
        graph.put(Pair.with(1, 8), 0);
        graph.put(Pair.with(8, 9), 0);
        graph.put(Pair.with(8, 10), 0);

        pairToEdgeName.put(Pair.with(0, 1), EdgeName.Reservation.toString());
        pairToEdgeName.put(Pair.with(1, 2), EdgeName.ScheduleResources.toString());
        pairToEdgeName.put(Pair.with(1, 3), EdgeName.ProcureAndReceivePowerWhips.toString());
        pairToEdgeName.put(Pair.with(1, 4), EdgeName.ProcureAndRecieveInfraMaterial.toString());
        pairToEdgeName.put(Pair.with(4, 5), EdgeName.InstallInfra.toString());
        pairToEdgeName.put(Pair.with(1, 6), EdgeName.ProcureAndReceiveCables.toString());
        pairToEdgeName.put(Pair.with(6, 7), EdgeName.PreCablePRD.toString());
        pairToEdgeName.put(Pair.with(1, 8), EdgeName.ArtifactGeneration.toString());
        pairToEdgeName.put(Pair.with(8, 9), EdgeName.UpstreamDeviceConfig.toString());
        pairToEdgeName.put(Pair.with(8, 10), EdgeName.GenerateCableMaps.toString());
    }


    /**
     * Testing function
     * @param args
     */
    public static void main(String[] args) {

    }        
}
