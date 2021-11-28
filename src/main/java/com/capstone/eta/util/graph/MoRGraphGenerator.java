/**
 * A util class that generates the graph on a certain date.
 * Call generateGraph() function to generate the graph on a specific date
 * Call main() function to test
 */
package com.capstone.eta.util.graph;
import java.util.Date;

import com.capstone.eta.util.data.Milestone;

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
     * Constructor, initialize the graph structure
     * @param deliveryNumber
     */
    public MoRGraphGenerator(String deliveryNumber) {
        this.deliveryNumber = deliveryNumber;
        this.n = 11;
        graph.put(Pair.with(0, 1), new Milestone(deliveryNumber, EdgeName.ProcureAndReceiveNetworkEquipment.toString(), GraphName.MoR.toString(), new Date(0)));
        graph.put(Pair.with(1, 2), new Milestone(deliveryNumber, EdgeName.HardwareInstallationAndValidation.toString(), GraphName.MoR.toString(), new Date(0)));
        graph.put(Pair.with(2, 11), new Milestone(deliveryNumber, EdgeName.CableAndConfiguration.toString(), GraphName.MoR.toString(), new Date(0)));
        graph.put(Pair.with(3, 4), new Milestone(deliveryNumber, EdgeName.ArtifactGeneration.toString(), GraphName.MoR.toString(), new Date(0)));
        graph.put(Pair.with(4, 5), new Milestone(deliveryNumber, EdgeName.GenerateCableMaps.toString(), GraphName.MoR.toString(), new Date(0)));
        graph.put(Pair.with(5, 11), new Milestone(deliveryNumber, EdgeName.CableAndConfiguration.toString(), GraphName.MoR.toString(), new Date(0)));
        graph.put(Pair.with(4, 10), new Milestone(deliveryNumber, EdgeName.UpstreamDeviceConfig.toString(), GraphName.MoR.toString(), new Date(0)));
        graph.put(Pair.with(6, 7), new Milestone(deliveryNumber, EdgeName.ScheduleResources.toString(), GraphName.MoR.toString(), new Date(0)));
        graph.put(Pair.with(7, 11), new Milestone(deliveryNumber, EdgeName.CableAndConfiguration.toString(), GraphName.MoR.toString(), new Date(0)));
        graph.put(Pair.with(8, 9), new Milestone(deliveryNumber, EdgeName.ProcureAndReceiveCables.toString(), GraphName.MoR.toString(), new Date(0)));
        graph.put(Pair.with(9, 11), new Milestone(deliveryNumber, EdgeName.CableAndConfiguration.toString(), GraphName.MoR.toString(), new Date(0)));

        // pairToEdgeName.put(Pair.with(0, 1), EdgeName.ProcureAndReceiveNetworkEquipment.toString());
        // pairToEdgeName.put(Pair.with(1, 2), EdgeName.HardwareInstallationAndValidation.toString());
        // pairToEdgeName.put(Pair.with(2, 11), EdgeName.CableAndConfiguration.toString());
        // pairToEdgeName.put(Pair.with(3, 4), EdgeName.ArtifactGeneration.toString());
        // pairToEdgeName.put(Pair.with(4, 5), EdgeName.GenerateCableMaps.toString());
        // pairToEdgeName.put(Pair.with(5, 11), EdgeName.CableAndConfiguration.toString());
        // pairToEdgeName.put(Pair.with(4, 10), EdgeName.UpstreamDeviceConfig.toString());
        // pairToEdgeName.put(Pair.with(6, 7), EdgeName.ScheduleResources.toString());
        // pairToEdgeName.put(Pair.with(7, 11), EdgeName.CableAndConfiguration.toString());
        // pairToEdgeName.put(Pair.with(8, 9), EdgeName.ProcureAndReceiveCables.toString());
        // pairToEdgeName.put(Pair.with(9, 11), EdgeName.CableAndConfiguration.toString());
    }


    /**
     * Testing function
     * @param args
     */
    public static void main(String[] args) {

    }    
}
