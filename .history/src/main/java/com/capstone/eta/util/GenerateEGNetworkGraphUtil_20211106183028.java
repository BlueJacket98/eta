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
    Map<String, Pair<Integer, Integer>> edgeNameToPair = new HashMap<>();

    GenerateEGNetworkGraphUtil() {

    }

    public static void main(String[] args) {
        
    }
}
