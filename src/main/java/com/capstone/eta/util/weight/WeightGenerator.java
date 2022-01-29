package com.capstone.eta.util.weight;
import java.math.BigDecimal;
import java.util.*;

import com.capstone.eta.dao.DeliveryInfoRepository;
import com.capstone.eta.entity.DeliveryInfo;
import com.capstone.eta.util.pmml.PMMLModel;
import com.capstone.eta.util.spring.ApplicationContextProvider;
public class WeightGenerator {
    private DeliveryInfoRepository deliveryInfoRepository;
    
    public WeightGenerator(){
        this.deliveryInfoRepository = (DeliveryInfoRepository) ApplicationContextProvider.getBean("deliveryInfoRepository");
    }

    public Integer getModelWeight(String deliveryNumber, String queryEdgeName, String graphName, Date date) {
        // model route
        PMMLModel model = new PMMLModel("src\\main\\java\\com\\capstone\\eta\\model\\MLP_0128.pmml");

        // Init paramData
        List<String> featureNames = model.getFeatureNames();
        Map<String, Object> paramData = new HashMap<>();
        for (String featureName : featureNames) {
            paramData.put(featureName, 0);
        }

        // Fill paramData
        // TODO: align model feature name MilestoneName_xxx with queryEdgeName in GraphGenerators
        DeliveryInfo delivery = deliveryInfoRepository.findByDeliveryNumber(deliveryNumber).get(0);
        // TaskGroupType
        if (paramData.containsKey("TaskGroupType_" + graphName)) {
            paramData.put("TaskGroupType_" + graphName, 1);
        } else {
            System.out.println("Key not exist: " + "TaskGroupType_" + graphName);
        }
        
        // MilestoneName
        if (paramData.containsKey("MilestoneName_" + queryEdgeName)) {
            paramData.put("MilestoneName_" + queryEdgeName, 1);
        } else {
            System.out.println("Key not exist: " + "MilestoneName_" + queryEdgeName);
        }
        
        // SLA Not one-hot encoded
        // TODO: need mysql table
        paramData.put("SLA", 10);
        
        // Region
        if (paramData.containsKey("Region_" + delivery.getRegion())) {
            paramData.put("Region_" + delivery.getRegion(), 1);
        } else {
            System.out.println("Key not exist: " + "Region_" + delivery.getRegion());
        }
        
        // DCCODE
        if (paramData.containsKey("DCCODE_" + delivery.getDcCode())) {
            paramData.put("DCCODE_" + delivery.getDcCode(), 1);
        } else {
            System.out.println("Key not exist: " + "DCCODE_" + delivery.getDcCode());
        }
        
        // ResourceType
        if (paramData.containsKey("ResourceType_" + delivery.getResoureType())) {
            paramData.put("ResourceType_" + delivery.getResoureType(), 1);
        } else {
            System.out.println("Key not exist: " + "ResourceType_" + delivery.getResoureType());
        }
        
        // Intent
        if (paramData.containsKey("Intent_" + delivery.getIntent())) {
            paramData.put("Intent_" + delivery.getIntent(), 1);
        } else {
            System.out.println("Key not exist: " + "Intent_" + delivery.getIntent());
        }
        
        // IsMainstream
        if (paramData.containsKey("IsMainstream_" + delivery.getIsMainstream())) {
            paramData.put("IsMainstream_" + delivery.getIsMainstream(), 1);
        } else {
            System.out.println("Key not exist: " + "IsMainstream_" + delivery.getIsMainstream());
        }
        
        // DeploymentSeverity Not one-hot encoded
        paramData.put("DeploymentSeverity", Integer.parseInt(delivery.getDeploymentSeverity()));
        // DeploymentPath
        if (paramData.containsKey("DeploymentPath_" + delivery.getDeploymentPath())) {
            paramData.put("DeploymentPath_" + delivery.getDeploymentPath(), 1);
        } else {
            System.out.println("Key not exist: " + "DeploymentPath_" + delivery.getDeploymentPath());
        }
        

        Map<String, Object> resultMap = model.modelPrediction(paramData);
        Integer result = getIntegerByObject(resultMap.get("Duration"));
        System.out.println("Predicted Duration: " + result);
        return result;   
    }

    public static Integer getHistWeight(String queryEdgeName, String graphName, Date date) {
        return 1;
    }

    public static Integer getIntegerByObject(Object object){
        Integer in = null;
        
        if(object!=null){
           if(object instanceof Integer){
              in = (Integer)object;
           }else if(object instanceof String){
              in = Integer.parseInt((String)object);
           }else if(object instanceof Double){
              in = (int)((double)object);
           }else if(object instanceof Float){
              in = (int)((float)object);
           }else if(object instanceof BigDecimal){
              in = ((BigDecimal)object).intValue();
           }else if(object instanceof Long){
              in = ((Long)object).intValue();
           }
        }
        
        return in;
     }

    public static void main(String[] args) {
    
    }

}
