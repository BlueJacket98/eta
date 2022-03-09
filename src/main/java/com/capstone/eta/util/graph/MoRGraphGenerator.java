/**
 * A util class that generates the graph on a certain date.
 * Call generateGraph() function to generate the graph on a specific date
 * Call main() function to test
 */
package com.capstone.eta.util.graph;
import java.util.*;

import com.capstone.eta.entity.WorkOrder;
import com.capstone.eta.util.data.Milestone;

import org.javatuples.*;

public class MoRGraphGenerator extends GraphGenerator {
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
        ScheduleResources,
        ProcureAndReceiveCables,
    }
    
    /**
     * Constructor, initialize the graph structure
     * @param deliveryNumber
     */
    public MoRGraphGenerator(String deliveryNumber, List<WorkOrder> startedTasksEntities) {
        this.deliveryNumber = deliveryNumber;
        this.n = 11;
        this.startedTasksEntities = startedTasksEntities;
        graph.put(Pair.with(0, 1), new Milestone(deliveryNumber, EdgeName.ProcureAndReceiveNetworkEquipment.toString(), GraphName.Mor.toString()));
        graph.put(Pair.with(1, 2), new Milestone(deliveryNumber, EdgeName.HardwareInstallationAndValidation.toString(), GraphName.Mor.toString()));
        graph.put(Pair.with(2, 11), new Milestone(deliveryNumber, EdgeName.CableAndConfiguration.toString(), GraphName.Mor.toString()));
        graph.put(Pair.with(3, 4), new Milestone(deliveryNumber, EdgeName.ArtifactGeneration.toString(), GraphName.Mor.toString()));
        graph.put(Pair.with(4, 5), new Milestone(deliveryNumber, EdgeName.GenerateCableMaps.toString(), GraphName.Mor.toString()));
        graph.put(Pair.with(5, 11), new Milestone(deliveryNumber, EdgeName.CableAndConfiguration.toString(), GraphName.Mor.toString()));
        graph.put(Pair.with(4, 10), new Milestone(deliveryNumber, EdgeName.UpstreamDeviceConfig.toString(), GraphName.Mor.toString()));
        graph.put(Pair.with(6, 7), new Milestone(deliveryNumber, EdgeName.ScheduleResources.toString(), GraphName.Mor.toString()));
        graph.put(Pair.with(7, 11), new Milestone(deliveryNumber, EdgeName.CableAndConfiguration.toString(), GraphName.Mor.toString()));
        graph.put(Pair.with(8, 9), new Milestone(deliveryNumber, EdgeName.ProcureAndReceiveCables.toString(), GraphName.Mor.toString()));
        graph.put(Pair.with(9, 11), new Milestone(deliveryNumber, EdgeName.CableAndConfiguration.toString(), GraphName.Mor.toString()));
    }


    /**
     * Testing function
     * @param args
     */
    public static void main(String[] args) {

    }    
}
