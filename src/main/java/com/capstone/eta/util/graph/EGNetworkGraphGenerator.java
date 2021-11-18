/**
 * A util class that generates the graph on a certain date.
 * Call generateGraph() function to generate the graph on a specific date
 * Call main() function to test
 */

package com.capstone.eta.util.graph;
import java.util.*;
import com.capstone.eta.util.weight.GenerateWeightUtil;
import org.javatuples.Pair;

public class EGNetworkGraphGenerator extends GraphGenerator {
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
    }

    /**
     * Constructor, initialize the graph structure with weight set to 0
     */
    public EGNetworkGraphGenerator() {
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
     * Testing function
     * @param args
     */
    public static void main(String[] args) {

    }
}
