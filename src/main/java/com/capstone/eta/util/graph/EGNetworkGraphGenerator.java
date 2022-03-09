/**
 * A util class that generates the graph on a certain date.
 * Call generateGraph() function to generate the graph on a specific date
 * Call main() function to test
 */

package com.capstone.eta.util.graph;
import java.util.*;

import com.capstone.eta.entity.WorkOrder;
import com.capstone.eta.util.data.Milestone;

import org.javatuples.Pair;

public class EGNetworkGraphGenerator extends GraphGenerator {
    enum GraphName {
        EngineeringGroupNetwork,
        Mor,
        PreRack,
        PreBuiltRow,
    }

    enum EdgeName {
        ProcureAndReceiveNetworkEquipment,
        HardwareInstallationAndValidation,
        CableAndConfiguration,
        ArtifactGeneration,
        GenerateCableMaps,
        UpstreamDeviceConfig,
    }

    /**
     * Constructor, initialize the graph structure
     * @param deliveryNumber
     */
    public EGNetworkGraphGenerator(String deliveryNumber, List<WorkOrder> startedTasksEntities) {
        this.deliveryNumber = deliveryNumber;
        this.n = 7;
        this.startedTasksEntities = startedTasksEntities;
        graph.put(Pair.with(0, 1), new Milestone(deliveryNumber, EdgeName.ProcureAndReceiveNetworkEquipment.toString(), GraphName.EngineeringGroupNetwork.toString()));
        graph.put(Pair.with(1, 2), new Milestone(deliveryNumber, EdgeName.HardwareInstallationAndValidation.toString(), GraphName.EngineeringGroupNetwork.toString()));
        graph.put(Pair.with(2, 7), new Milestone(deliveryNumber, EdgeName.CableAndConfiguration.toString(), GraphName.EngineeringGroupNetwork.toString()));
        graph.put(Pair.with(3, 4), new Milestone(deliveryNumber, EdgeName.ArtifactGeneration.toString(), GraphName.EngineeringGroupNetwork.toString()));
        graph.put(Pair.with(4, 5), new Milestone(deliveryNumber, EdgeName.GenerateCableMaps.toString(), GraphName.EngineeringGroupNetwork.toString()));
        graph.put(Pair.with(4, 6), new Milestone(deliveryNumber, EdgeName.UpstreamDeviceConfig.toString(), GraphName.EngineeringGroupNetwork.toString()));
        graph.put(Pair.with(5, 7), new Milestone(deliveryNumber, EdgeName.CableAndConfiguration.toString(), GraphName.EngineeringGroupNetwork.toString()));
    }

    /**
     * Testing function
     * @param args
     */
    public static void main(String[] args) {

    }
}
