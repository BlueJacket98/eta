package com.capstone.eta.util.graph;
import org.javatuples.*;
import java.util.*;

import com.capstone.eta.entity.WorkOrder;
import com.capstone.eta.util.data.Milestone;

public class PreBuiltRowGraphGenerator extends GraphGenerator {
    enum GraphName {
        EngineeringGroupNetwork,
        Mor,
        PreRack,
        PreBuiltRow,
    }
    
    enum EdgeName {
        Reservation,
        ProcureAndReceiveCables,
        ProcureAndRecieveInfraMaterial,
        ProcureAndReceivePowerWhips,
        ScheduleResources,
        InstallInfra,
    }

    /**
     * Constructor, initialize the graph structure
     * @param deliveryNumber
     */
    public PreBuiltRowGraphGenerator(String deliveryNumber, List<WorkOrder> startedTasksEntities) {
        this.deliveryNumber = deliveryNumber;
        this.n = 10;
        this.startedTasksEntities = startedTasksEntities;
        graph.put(Pair.with(0, 1), new Milestone(deliveryNumber, EdgeName.Reservation.toString(), GraphName.PreRack.toString()));
        graph.put(Pair.with(1, 2), new Milestone(deliveryNumber, EdgeName.ProcureAndReceiveCables.toString(), GraphName.PreRack.toString()));
        graph.put(Pair.with(1, 3), new Milestone(deliveryNumber, EdgeName.ProcureAndRecieveInfraMaterial.toString(), GraphName.PreRack.toString()));
        graph.put(Pair.with(1, 4), new Milestone(deliveryNumber, EdgeName.ProcureAndReceivePowerWhips.toString(), GraphName.PreRack.toString()));
        graph.put(Pair.with(1, 5), new Milestone(deliveryNumber, EdgeName.ScheduleResources.toString(), GraphName.PreRack.toString()));
        graph.put(Pair.with(3, 6), new Milestone(deliveryNumber, EdgeName.InstallInfra.toString(), GraphName.PreRack.toString()));

    }

}
