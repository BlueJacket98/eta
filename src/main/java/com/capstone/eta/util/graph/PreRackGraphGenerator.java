/**
 * A util class that generates the graph on a certain date.
 * Call generateGraph() function to generate the graph on a specific date
 * Call main() function to test
 */
package com.capstone.eta.util.graph;
import java.util.Date;

import com.capstone.eta.util.data.Milestone;

import org.javatuples.*;

public class PreRackGraphGenerator extends GraphGenerator {
    enum GraphName {
        EngineeringGroupNetwork,
        Mor,
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
     * Constructor, initialize the graph structure
     * @param deliveryNumber
     */
    public PreRackGraphGenerator(String deliveryNumber) {
        this.deliveryNumber = deliveryNumber;
        this.n = 10;

        graph.put(Pair.with(0, 1), new Milestone(deliveryNumber, EdgeName.Reservation.toString(), GraphName.PreRack.toString(), new Date(0)));
        graph.put(Pair.with(1, 2), new Milestone(deliveryNumber, EdgeName.ScheduleResources.toString(), GraphName.PreRack.toString(), new Date(0)));
        graph.put(Pair.with(1, 3), new Milestone(deliveryNumber, EdgeName.ProcureAndReceivePowerWhips.toString(), GraphName.PreRack.toString(), new Date(0)));
        graph.put(Pair.with(1, 4), new Milestone(deliveryNumber, EdgeName.ProcureAndRecieveInfraMaterial.toString(), GraphName.PreRack.toString(), new Date(0)));
        graph.put(Pair.with(4, 5), new Milestone(deliveryNumber, EdgeName.InstallInfra.toString(), GraphName.PreRack.toString(), new Date(0)));
        graph.put(Pair.with(1, 6), new Milestone(deliveryNumber, EdgeName.ProcureAndReceiveCables.toString(), GraphName.PreRack.toString(), new Date(0)));
        graph.put(Pair.with(6, 7), new Milestone(deliveryNumber, EdgeName.PreCablePRD.toString(), GraphName.PreRack.toString(), new Date(0)));
        graph.put(Pair.with(1, 8), new Milestone(deliveryNumber, EdgeName.ArtifactGeneration.toString(), GraphName.PreRack.toString(), new Date(0)));
        graph.put(Pair.with(8, 9), new Milestone(deliveryNumber, EdgeName.UpstreamDeviceConfig.toString(), GraphName.PreRack.toString(), new Date(0)));
        graph.put(Pair.with(8, 10), new Milestone(deliveryNumber, EdgeName.GenerateCableMaps.toString(), GraphName.PreRack.toString(), new Date(0)));

        // pairToEdgeName.put(Pair.with(0, 1), EdgeName.Reservation.toString());
        // pairToEdgeName.put(Pair.with(1, 2), EdgeName.ScheduleResources.toString());
        // pairToEdgeName.put(Pair.with(1, 3), EdgeName.ProcureAndReceivePowerWhips.toString());
        // pairToEdgeName.put(Pair.with(1, 4), EdgeName.ProcureAndRecieveInfraMaterial.toString());
        // pairToEdgeName.put(Pair.with(4, 5), EdgeName.InstallInfra.toString());
        // pairToEdgeName.put(Pair.with(1, 6), EdgeName.ProcureAndReceiveCables.toString());
        // pairToEdgeName.put(Pair.with(6, 7), EdgeName.PreCablePRD.toString());
        // pairToEdgeName.put(Pair.with(1, 8), EdgeName.ArtifactGeneration.toString());
        // pairToEdgeName.put(Pair.with(8, 9), EdgeName.UpstreamDeviceConfig.toString());
        // pairToEdgeName.put(Pair.with(8, 10), EdgeName.GenerateCableMaps.toString());
    }


    /**
     * Testing function
     * @param args
     */
    public static void main(String[] args) {

    }        
}
