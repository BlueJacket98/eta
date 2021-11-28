package com.capstone.eta.util.json;
import java.io.*;
import java.util.*;
import java.lang.reflect.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;


public class JsonUtil {
    enum GraphName {
        EGNetwork,
        MoR,
        PreRack,
    }

    /**
     * 
     * @param graphName
     * @param milestoneName
     * @return
     */
    public static Integer getMilestoneSLAFromGraphAndMilestoneName(String graphName, String milestoneName) {
        JsonObject configJsonObject = getConfigJsonObjectFromGraphName(graphName);
        return getMilestoneSLAFromConfig(configJsonObject, milestoneName);
    }

    /**
     * 
     * @param graphName
     * @return
     */
    private static JsonObject getConfigJsonObjectFromGraphName(String graphName) {
        String path = "src\\main\\java\\com\\capstone\\eta\\config\\sla\\" + graphName + "Config.json";
        JsonObject configJsonObject = getJsonObject(path);
        return configJsonObject;        
    }

    /**
     * 
     * @param configJsonObject
     * @param milestoneName
     * @return
     */
    private static Integer getMilestoneSLAFromConfig(JsonObject configJsonObject, String milestoneName) {
        JsonElement slaObject = configJsonObject.getAsJsonObject("milestones")
                                                .getAsJsonObject(milestoneName)
                                                .get("sla");
        Gson gson = new Gson();
        Type slaType = new TypeToken<Integer>() {}.getType();
        Integer sla = gson.fromJson(slaObject, slaType);
        return sla;        
    }

    /**
     * Get SLA Map with graphName and milestoneName as input
     * Make sure the config files are named by GraphName + "Config.json"
     * @param graphName
     * @param milestoneName
     * @return a map, key being the task name, value being the task's sla
     */
    public static Map<String, Integer> getTasksSLAMapFromGraphAndMilestoneName(String graphName, String milestoneName) {
        JsonObject configJsonObject = getConfigJsonObjectFromGraphName(graphName);
        return getTasksSLAMapFromConfig(configJsonObject, milestoneName);
    }

    /**
     * Get SLA Map with configJsonObject as input
     * @param configJsonObject
     * @param milestoneName
     * @return a map, key being the task name, value being the task's sla
     */
    private static Map<String, Integer> getTasksSLAMapFromConfig(JsonObject configJsonObject, String milestoneName) {
        JsonElement slaMapObject = configJsonObject.getAsJsonObject("milestones")
                                                   .getAsJsonObject(milestoneName)
                                                   .get("tasksSLA");
        Gson gson = new Gson();
        Type slaMapType = new TypeToken<Map<String, Integer>>() {}.getType();
        Map<String, Integer> slaMap = gson.fromJson(slaMapObject, slaMapType);
        return slaMap;
    }

    /**
     * Read .json file to JsonObject
     * @param path
     * @return JsonObject that corresponds to the file
     */
    private static JsonObject getJsonObject(String path) { 
        String input = readJsonFile(path);
        return new Gson().fromJson(input, JsonObject.class);
    }

    /**
     * Read .json file to String
     * @param fileName
     * @return String that corresponds to the file
     */
    private static String readJsonFile(String fileName) {
        String jsonStr = "";
        try {
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        
    }
}
