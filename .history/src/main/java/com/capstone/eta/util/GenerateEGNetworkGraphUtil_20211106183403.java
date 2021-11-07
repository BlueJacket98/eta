package com.capstone.eta.util;
import java.util.*;
import org.javatuples.Pair;

public class GenerateEGNetworkGraphUtil {
    enum EdgeName {
        ProcureAndReceiveNetworkEquipment,
        HardwareInstallationAndValidation,
        CableAndConfiguration,
        ArtifactGeneration,
        GenerateCableMaps,
        UpstreamDeviceConfig,
    }

    Map<Pair<Integer, Integer>, Integer> graph = new HashMap<>();
    Map<String, List<Pair<Integer, Integer>>> edgeNameToPairs = new HashMap<>();

    GenerateEGNetworkGraphUtil() {
        graph.put(Pair.with(0, 1), 0);
        graph.put(Pair.with(1, 2), 0);
        graph.put(Pair.with(2, 7), 0);
        graph.put(Pair.with(3, 4), 0);
        graph.put(Pair.with(4, 5), 0);
        graph.put(Pair.with(4, 6), 0);
        graph.put(Pair.with(5, 7), 0);

        edgeNameToPair.put()
    }

    public static void main(String[] args) {
        
    }
}
