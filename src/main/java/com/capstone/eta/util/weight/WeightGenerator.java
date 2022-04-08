package com.capstone.eta.util.weight;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import com.capstone.eta.dao.DeliveryInfoRepository;
import com.capstone.eta.entity.DeliveryInfo;
import com.capstone.eta.util.pmml.PMMLModel;
import com.capstone.eta.util.spring.ApplicationContextProvider;

import org.apache.ibatis.binding.MapperMethod.ParamMap;
public class WeightGenerator {
    private static WeightGenerator wg = null;
    private DeliveryInfoRepository deliveryInfoRepository;
    private PMMLModel model;
    List<String> featureNames;
    Map<String, Object> paramData;
    
    private WeightGenerator(){
        this.deliveryInfoRepository = (DeliveryInfoRepository) ApplicationContextProvider.getBean("deliveryInfoRepository");
        initModel();
    }

    private void initModel(){
        // model route
        model = new PMMLModel("src\\main\\java\\com\\capstone\\eta\\model\\MLP_0128.pmml");
        // Init paramData
        featureNames = model.getFeatureNames();

        
    }

    public static WeightGenerator getInstance() {
        if (wg == null) {
            wg = new WeightGenerator();
        }
        return wg;
    }

    public Integer getModelWeight(String deliveryNumber, String queryEdgeName, String graphName, Date date, Float sla) {
        // Fill paramData
        paramData = new HashMap<>();
        for (String featureName : featureNames) {
            paramData.put(featureName, 0);
        }
        // TODO: align model feature name MilestoneName_xxx with queryEdgeName in GraphGenerators
        DeliveryInfo delivery = deliveryInfoRepository.findByDeliveryNumber(deliveryNumber).get(0);
        // TaskGroupType
        if (paramData.containsKey("TaskGroupType_" + graphName)) {
            paramData.put("TaskGroupType_" + graphName, 1);
            // System.out.println("TaskGroupType_" + graphName + ": " + "1");
        } else {
            // System.out.println("Key not exist: " + "TaskGroupType_" + graphName);
        }
        
        // MilestoneName
        if (paramData.containsKey("MilestoneName_" + queryEdgeName)) {
            paramData.put("MilestoneName_" + queryEdgeName, 1);
            // System.out.println("MilestoneName_" + queryEdgeName + ": " + "1");
        } else {
            // System.out.println("Key not exist: " + "MilestoneName_" + queryEdgeName);
        }
        
        // SLA Not one-hot encoded
        paramData.put("SLA", sla);
        // System.out.println("SLA" + ": " + sla);
        // Region
        if (paramData.containsKey("Region_" + delivery.getRegion())) {
            paramData.put("Region_" + delivery.getRegion(), 1);
            // System.out.println("Region_" + delivery.getRegion() + ": " + "1");
        } else {
            // System.out.println("Key not exist: " + "Region_" + delivery.getRegion());
        }
        
        // DCCODE
        if (paramData.containsKey("DCCODE_" + delivery.getDcCode())) {
            paramData.put("DCCODE_" + delivery.getDcCode(), 1);
            // System.out.println("DCCODE_" + delivery.getDcCode() + ": " + "1");
        } else {
            // System.out.println("Key not exist: " + "DCCODE_" + delivery.getDcCode());
        }
        
        // ResourceType
        if (paramData.containsKey("ResourceType_" + delivery.getResoureType())) {
            paramData.put("ResourceType_" + delivery.getResoureType(), 1);
            // System.out.println("ResourceType_" + delivery.getResoureType() + ": " + "1");
        } else {
            // System.out.println("Key not exist: " + "ResourceType_" + delivery.getResoureType());
        }
        
        // Intent
        if (paramData.containsKey("Intent_" + delivery.getIntent())) {
            paramData.put("Intent_" + delivery.getIntent(), 1);
            // System.out.println("Intent_" + delivery.getIntent() + ": " + "1");
        } else {
            // System.out.println("Key not exist: " + "Intent_" + delivery.getIntent());
        }
        
        // IsMainstream
        if (paramData.containsKey("IsMainstream_" + delivery.getIsMainstream())) {
            paramData.put("IsMainstream_" + delivery.getIsMainstream(), 1);
            // System.out.println("IsMainstream_" + delivery.getIsMainstream() + ": " + "1");
        } else {
            // System.out.println("Key not exist: " + "IsMainstream_" + delivery.getIsMainstream());
        }
        
        // DeploymentSeverity Not one-hot encoded
        paramData.put("DeploymentSeverity", Integer.parseInt(delivery.getDeploymentSeverity()));
        // System.out.println("DeploymentSeverity" + ": " + Integer.parseInt(delivery.getDeploymentSeverity()));
        // DeploymentPath
        if (paramData.containsKey("DeploymentPath_" + delivery.getDeploymentPath())) {
            paramData.put("DeploymentPath_" + delivery.getDeploymentPath(), 1);
            // System.out.println("DeploymentPath_" + delivery.getDeploymentPath() + ": " + "1");
        } else {
            // System.out.println("Key not exist: " + "DeploymentPath_" + delivery.getDeploymentPath());
        }
        // System.out.println("ParamData: " + paramData);
        // long startTime = System.currentTimeMillis();
        Map<String, Object> resultMap = model.modelPrediction(paramData);
        // System.out.println("Pred time: " + (System.currentTimeMillis() - startTime));
        // System.out.println(resultMap);
        if (resultMap.get("Duration") == null) {
            return -1;
        }
        Integer result = getIntegerByObject(resultMap.get("Duration"));
        // System.out.println("Predicted Duration: " + result);
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
        String mapString = "DCCODE_AMS22=0, DCCODE_AMS23=0, DeploymentPath_WHSE2PBR=0, DCCODE_AMS24=0, MilestoneName_GenerateCableMaps=0, DCCODE_TLH30=0, DCCODE_AMS20=0, DCCODE_MEM30=0, ResourceType_Search-NW-FPGA=0, MilestoneName_HardwareInstallationAndValidation=0, Region_japan east=0, Region_Australia Central=0, DCCODE_dsm07=0, Region_Brazil South=0, ResourceType_XIO Storage=0, Region_west europe=0, Region_KOREA SOUTH=0, DCCODE_LON26=0, Region_South Central US STG=0, Region_East US 2=0, DCCODE_LON24=0, DCCODE_LON23=0, Region_APAC Southeast 2=0, DCCODE_LON22=0, DCCODE_LON21=0, Region_South India=0, DCCODE_BN10=0, DCCODE_BN11=0, Region_south africa north=0, DCCODE_CPQ02=0, DCCODE_MWH20=0, DCCODE_MWH21=0, Intent_DecommPair=0, DCCODE_BOS32=0, Region_Sweden Central=0, DCCODE_dsm10=0, ResourceType_GPU Compute 6.2=0, DCCODE_MEL23=0, MilestoneName_ProcureAndReceiveNetworkEquipment=0, Region_EAST US 2=0, ResourceType_BM SAP Network=0, ResourceType_ActiveDir-S=0, TaskGroupType_PreBuiltRow=0, Region_UK SOUTH=0, Region_east us 2=0, DCCODE_bn10=0, DCCODE_VIE=0, ResourceType_Pre-BuiltRow=0, DCCODE_MNZ21=0, DCCODE_MNZ20=0, Region_South Africa West=0, DCCODE_SVG20=0, DCCODE_MNZ22=0, DCCODE_PHL30=0, Region_UK South=0, DCCODE_BN12=0, DCCODE_BN13=0, Region_france central=0, DCCODE_CPQ22=0, DCCODE_CPQ21=0, DCCODE_CPQ24=0, DCCODE_CPQ23=0, ResourceType_BM HSM=0, DCCODE_PAR21=0, Region_SWITZERLAND WEST=0, DCCODE_PAR20=0, DCCODE_MWH04=0, DCCODE_PAR23=0, DCCODE_HOU30=0, DCCODE_MWH03=0, DeploymentPath_Bld2Ord2NonPBR=0, DCCODE_DM4=0, DCCODE_dm4=0, DCCODE_ATL33=1, DCCODE_MWH05=0, DCCODE_PAR24=0, TaskGroupType_Mor=0, DCCODE_CPQ20=0, DCCODE_DM2=0, ResourceType_Lv2 Compute=0, DCCODE_DM3=0, ResourceType_Remote Viz Gen7=0, ResourceType_FPGA STP=0, Region_CHINA NORTH 2=0, DCCODE_HYD30=0, ResourceType_HPC AMD 8.0=0, ResourceType_BackEnd-S=0, MilestoneName_CableAndConfigureNetwork=0, Region_East US 2 EUAP=0, ResourceType_GPU Compute-T4=0, DCCODE_GVA20=0, DCCODE_YQB20=0, Region_USGov Arizona=0, DCCODE_hkg23=0, DCCODE_hkg22=0, DCCODE_CWL20=0, DCCODE_hkg21=0, DCCODE_JNB22=0, DCCODE_SHA20=0, ResourceType_EOP-FrontEnd=0, DCCODE_JNB21=0, MilestoneName_UpstreamDeviceConfig=0, DCCODE_JNB20=0, Intent_Reservation=0, DCCODE_JNB23=0, Region_Central US=1, DCCODE_BER21=0, ResourceType_MacPro=0, ResourceType_RescueDNS=0, DCCODE_BKK30=0, DCCODE_DEN30=0, Region_SWITZERLAND NORTH=0, DCCODE_phx80=0, MilestoneName_ScheduleResources=0, DCCODE_sel22=0, DCCODE_yto23=0, DCCODE_sel20=0, DCCODE_yto22=0, DCCODE_yto21=0, DCCODE_RED01=0, DCCODE_SG2=0, DCCODE_SJC20=0, DCCODE_SEL20=0, DCCODE_SJC21=0, DCCODE_SEL22=0, DCCODE_SEL21=0, ResourceType_Confidential Compute=0, DCCODE_CH1=0, DCCODE_CH2=0, Region_central india=0, TaskGroupType_EngineeringGroupNetwork=0, Region_south central us=0, Region_USNat East=0, ResourceType_ZRS StorageFast=0, DCCODE_SN6=0, DCCODE_SN7=0, DCCODE_sn7=0, DCCODE_SN8=0, Region_norway east=0, SLA=11, Region_USGov Texas=0, DCCODE_SN1=0, DCCODE_SN3=0, Region_Switzerland North=0, DCCODE_SN4=0, DCCODE_sn4=0, DCCODE_SN5=0, DCCODE_OSA22=0, DCCODE_OSA21=0, DCCODE_OSA20=0, DCCODE_CBR21=0, DCCODE_CBR20=0, DCCODE_zrh22=0, ResourceType_OneDrive=0, DCCODE_pnq22=0, DCCODE_CO1=0, DCCODE_DFW31=0, DCCODE_CO3=0, ResourceType_EOP-FT=0, DCCODE_CO6=0, DCCODE_co6=0, Region_USSec East=0, Region_USSec West Central=0, IsMainstream_Not Mainstream=1, DCCODE_pnq20=0, ResourceType_BM VMW VS COMP2=0, ResourceType_BM VMW VS COMP1=0, DCCODE_SAT09=0, ResourceType_HPC-HBv2=0, DCCODE_OSA31=0, DCCODE_KUL01=0, Region_Australia Central 2=0, ResourceType_GPU Remote-Viz Gen 5.0=0, Region_WEST US 2=0, DCCODE_BL2=0, DCCODE_bl2=0, DCCODE_BL3=0, DCCODE_bl3=0, Region_West US 2=0, Region_West US 3=0, DCCODE_MRS21=0, Region_WEST EUROPE=0, Region_FRANCE SOUTH=0, Region_Central India=0, Region_canada east=0, Region_AUSTRALIA CENTRAL=0, DCCODE_OSA02=0, ResourceType_GPU ComputeA100=0, Region_west us 3=0, Region_west us 2=0, DCCODE_GVX21=0, DCCODE_SLA=0, DCCODE_SAT10=0, DCCODE_SAT11=0, DCCODE_CYS06=0, DCCODE_CYS08=0, DCCODE_CBR22=0, DCCODE_HEL02=0, DCCODE_PUS20=0, DCCODE_CYS05=0, DCCODE_GVX11=0, Region_Korea South=0, DCCODE_CYS04=0, DCCODE_SAT20=0, Intent_Test-Prod=0, DCCODE_BJ1=0, DCCODE_CYS01=0, DCCODE_SAT21=0, ResourceType_BM PHSM=0, DCCODE_mel23=0, ResourceType_Search=0, Intent_W2L=0, Region_South Africa North=0, DCCODE_osl22=0, DCCODE_osl21=0, DCCODE_GRN01=0, Region_France South=0, DCCODE_CHI21=0, Region_Norway East=0, DeploymentSeverity=4, Region_North Europe=0, DCCODE_dub12=0, DCCODE_PUS04=0, ResourceType_Storage=0, Intent_Test=0, Region_QATAR CENTRAL=0, Region_Korea Central=0, Region_north europe=0, ResourceType_CloudDNS=0, DCCODE_FRA21=0, DCCODE_FRA22=0, DCCODE_FRA23=0, TaskGroupType_PreRack=1, Region_USDoD Central=0, DCCODE_LVL02=0, DCCODE_CPT20=0, DCCODE_LVL01=0, DCCODE_AMS08=0, DCCODE_AMS09=0, DCCODE_AMS04=0, DCCODE_AMS06=0, DCCODE_AMS07=0, DCCODE_dub20=0, DCCODE_bl22=0, Region_east asia=0, DCCODE_bl24=0, DCCODE_AMS11=0, DCCODE_BL22=0, DCCODE_BL21=0, DCCODE_BL24=0, DCCODE_NTG20=0, Region_Australia Southeast=0, DCCODE_BL23=0, DCCODE_CHI30=0, MilestoneName_InstallInfra=0, DCCODE_SIN22=0, DCCODE_SIN21=0, DCCODE_SIN20=0, DCCODE_BN7=0, DCCODE_BN8=0, DCCODE_BN9=0, ResourceType_SDN Appliance=0, Region_SOUTH INDIA=0, Region_Sweden South=0, DCCODE_BN1=0, DCCODE_bn1=0, ResourceType_Cosmos=0, DCCODE_BN3=0, DCCODE_BN4=0, Region_West Europe=0, ResourceType_ZRS Storage=0, ResourceType_Game Streaming=0, DCCODE_HNL01=0, Intent_Growth=1, DCCODE_ZQZ22=0, ResourceType_Compute-AZSC=0, ResourceType_Compute-FAST=0, Region_UAE North=0, ResourceType_Cosmos Store=0, IsMainstream_Is Mainstream=0, ResourceType_MSODS=0, DCCODE_DOH31=0, DCCODE_DOH30=0, DCCODE_SYD03=0, DCCODE_ZQZ20=0, DCCODE_ams08=0, DCCODE_ams09=0, Region_UAE Central=0, DCCODE_RIO20=0, DCCODE_par24=0, ResourceType_Dedicated Compute=0, DCCODE_jnb23=0, DCCODE_fra22=0, Region_North Central US=0, DCCODE_fra21=0, ResourceType_BackEnd=0, DCCODE_DOH21=0, MilestoneName_ProcureAndReceiveCables=0, DCCODE_lon23=0, DCCODE_lon24=0, ResourceType_BM CRAY CS-ETH=0, DCCODE_par20=0, ResourceType_Mv2-Series=0, DCCODE_SYD27=0, Intent_Warehouse=0, Region_uk south=0, Region_switzerland north=0, Intent_Pilot-Eng=0, DCCODE_DSM06=0, Region_uk west=0, DCCODE_DSM07=0, DCCODE_DSM08=0, DCCODE_STO=0, DCCODE_DSM09=0, DCCODE_BJS20=0, DCCODE_SYD25=0, DeploymentPath_WHSE2NonPBR=1, DCCODE_SYD26=0, ResourceType_Torus=0, DCCODE_SYD24=0, DCCODE_SYD21=0, DCCODE_SYD22=0, ResourceType_Xtransport=0, Region_EAST US 2 EUAP=0, DCCODE_mnz22=0, ResourceType_BM CRAY NW 1=0, ResourceType_Compute=0, ResourceType_EOP-FrontEnd-CA=0, Region_japan west=0, DCCODE_DSM10=0, Region_NORTH CENTRAL US=0, DCCODE_TPE31=0, Region_East Asia=0, DCCODE_EWR30=0, Region_Australia East=0, DCCODE_BY3=0, ResourceType_ACS=0, Region_west central us=0, DeploymentPath_Pre-BuiltRow=0, Region_Japan East=0, DCCODE_DXB21=0, DCCODE_GVX01=0, DCCODE_PHX80=0, Region_JAPAN EAST=0, DCCODE_cbr21=0, DCCODE_HKG22=0, ResourceType_ADL-Cosmos=0, DCCODE_HKG21=0, DCCODE_cwl20=0, DCCODE_YTO22=0, Intent_Pilot=0, DCCODE_YTO21=0, ResourceType_FrontEnd-S=0, DCCODE_YTO23=0, DCCODE_MAA20=0, DCCODE_MEX31=0, Region_UAE CENTRAL=0, Region_USNat West=0, DCCODE_YTO30=0, DCCODE_HKG20=0, ResourceType_Compute-SPO=0, DCCODE_SAO31=0, DCCODE_tyo22=0, Region_east us=0, DCCODE_MIL30=0, DCCODE_tyo20=0, DCCODE_PHX70=0, DCCODE_yqb20=0, ResourceType_PilotFish=0, Region_China North 10=0, DCCODE_LAS31=0, DCCODE_TYA=0, ResourceType_MHSM=0, DCCODE_mwh05=0, Region_China East 3=0, Region_West India=0, DCCODE_PER31=0, Region_China East 2=0, DCCODE_MMA01=0, Region_Germany West Central=0, DCCODE_PR1=0, Region_Canada East=0, ResourceType_EOP-BackEnd=0, Region_CENTRAL US=0, DCCODE_TYO01=0, Region_France Central=0, Region_Brazil Southeast=0, Region_WEST US=0, Region_Switzerland West=0, DCCODE_gvx11=0, ResourceType_EOP-WS=0, DCCODE_TYO31=0, Region_South Central US=0, DCCODE_lvl02=0, DCCODE_lvl01=0, Region_Japan West=0, ResourceType_BM NetApp=0, Region_JAPAN WEST=0, ResourceType_StorageFast=0, ResourceType_G-ZRS Storage=0, DCCODE_BRU30=0, DCCODE_TYO20=0, DCCODE_TYO22=0, ResourceType_GPU A100 Gen8.0=0, Region_BRAZIL SOUTH=0, Region_Southeast Asia=0, Region_Norway West=0, DCCODE_osa22=0, ResourceType_Management=0, MilestoneName_PreCablePRD=0, Region_Germany North=0, DCCODE_CVG30=0, DCCODE_ZRH20=0, DCCODE_DTT31=0, ResourceType_CloudBuild=0, IsMainstream_Pre-BuiltRow=0, MilestoneName_ProcureAndReceiveInfraMaterials=0, Region_canada central=0, Region_NORWAY EAST=0, DCCODE_PNQ22=0, Region_Austria East=0, DCCODE_PHX21=0, ResourceType_ADL Storage=0, DCCODE_gvx21=0, DCCODE_MAA01=0, DCCODE_cys01=0, DCCODE_TPA30=0, Intent_Secondary=0, DCCODE_cys08=0, ResourceType_DCFrontDoor=0, Region_USSec West=0, DCCODE_PNQ30=0, ResourceType_Cafe=0, ResourceType_M-Series=0, DCCODE_DUB24=0, DCCODE_syd24=0, ResourceType_Xarchive=0, DCCODE_syd26=0, ResourceType_Service Fabric=0, DCCODE_PHX10=0, Region_USGov Virginia=0, DCCODE_MSP31=0, ResourceType_Compute HPC Gen 6.0=0, Region_korea central=0, Region_West US=0, Region_WEST CENTRAL US=0, DCCODE_PNQ21=0, DCCODE_syd27=0, DCCODE_PNQ20=0, ResourceType_BM VMW VS MSEE=0, ResourceType_FrontDoor=0, Region_australia central 2=0, Region_East US=0, DCCODE_AUH20=0, ResourceType_ASFConverged=0, DCCODE_BOM01=0, Region_SOUTH CENTRAL US=0, Region_East US STG=0, ResourceType_FPGA Compute6.0=0, DCCODE_DUB07=0, Region_Central US EUAP=0, Region_UK West=0, DCCODE_DUB09=0, DCCODE_DUB08=0, DCCODE_OSL20=0, ResourceType_GPU-v Compute Gen 5.0=0, Region_australia southeast=0, DCCODE_DB3=0, DCCODE_DB4=0, DCCODE_db4=0, DCCODE_DB5=0, DCCODE_OSL23=0, DCCODE_OSL22=0, DCCODE_OSL21=0, ResourceType_EOP-Core=0, Region_China North 3=0, DCCODE_ams22=0, ResourceType_Compute HF=0, ResourceType_EOP-BackEnd-CA=0, Region_sweden central=0, DCCODE_ams23=0, MilestoneName_ArtifactGeneration=1, Region_USDoD East=0, Region_China North 2=0, DCCODE_PRG01=0, DCCODE_DUB10=0, Region_southeast asia=0, DCCODE_DUB12=0, MilestoneName_ProcureAndReceivePowerWhips=0, Region_West Central US=0, Region_Canada Central=0, Region_germany west central=0, DCCODE_sin22=0, DCCODE_sin21=0, ResourceType_Substrate=0, Intent_Pilot-Prod=0, DCCODE_ams11=0, Region_BRAZIL SOUTHEAST=0, Region_EAST US=0, Region_SOUTHEAST ASIA=0, Region_australia east=0, Region_central us=0, ResourceType_XDirect=0, DCCODE_DUB21=0, DCCODE_DUB20=0, Region_GERMANY WEST CENTRAL=0";
        PMMLModel model = new PMMLModel("src\\main\\java\\com\\capstone\\eta\\model\\MLP_0128.pmml");

        List<String> featureNames = model.getFeatureNames();

        String[] pairs = mapString.split(", ");
        Map<String, Object> paramData = new HashMap<>();
        for (String featureName : featureNames) {
            paramData.put(featureName, 0);
        }
        for (int i = 0; i < pairs.length; i++) {
            String pair = pairs[i];
            String[] keyValue = pair.split("=");
            paramData.put(keyValue[0], Integer.valueOf(keyValue[1]));
        }

        System.out.println(paramData);


        Map<String, Object> resultMap = model.modelPrediction(paramData);
        System.out.println(resultMap);
    }

}

