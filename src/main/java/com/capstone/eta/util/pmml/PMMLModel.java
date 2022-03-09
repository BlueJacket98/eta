package com.capstone.eta.util.pmml;

import org.dmg.pmml.FieldName;
import org.dmg.pmml.PMML;
import org.jpmml.evaluator.*;
import org.jpmml.model.PMMLUtil;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class PMMLModel {
    private static Evaluator modelEvaluator;

    /**
     * 通过传入 PMML 文件路径来生成机器学习模型
     * @param pmmlFileName pmml 文件路径
     */
    public PMMLModel(String pmmlFileName) {
        PMML pmml = null;

        try {
            if (pmmlFileName != null) {
                InputStream is = new FileInputStream(pmmlFileName);
                pmml = PMMLUtil.unmarshal(is);
                try {
                    is.close();
                } catch (IOException e) {
                    System.out.println("InputStream close error!");
                }

                ModelEvaluatorFactory modelEvaluatorFactory = ModelEvaluatorFactory.newInstance();

                modelEvaluator = (Evaluator) modelEvaluatorFactory.newModelEvaluator(pmml);
                modelEvaluator.verify();
                System.out.println("Model Loaded!");
            }
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public Map<String, Object> modelPrediction(Map<String, Object> paramData) {
        if (modelEvaluator == null || paramData == null) {
            System.out.println("Evaluator or input null!");
            return null;
        }

        List<InputField> inputFields = modelEvaluator.getInputFields();   //获取模型的输入域
        Map<FieldName, FieldValue> arguments = new LinkedHashMap<>();

        if (inputFields.size() != paramData.size()) {
            System.out.println("inputFields.size(): " + inputFields.size());
            System.out.println("paramData.size(): " + paramData.size());
            System.out.println("Input data dimension not aligned!");
        }

        for (InputField inputField : inputFields) {            //将参数通过模型对应的名称进行添加
            FieldName inputFieldName = inputField.getName();   //获取模型中的参数名
            Object paramValue = paramData.get(inputFieldName.getValue());   //获取模型参数名对应的参数值
            FieldValue fieldValue = inputField.prepare(paramValue);   //将参数值填入模型中的参数中
            arguments.put(inputFieldName, fieldValue);          //存放在map列表中
        }
        Map<FieldName, ?> results = modelEvaluator.evaluate(arguments);
        List<TargetField> targetFields = modelEvaluator.getTargetFields();

        Map<String, Object> resultMap = new HashMap<>();

        for(TargetField targetField : targetFields) {
            FieldName targetFieldName = targetField.getName();
            Object targetFieldValue = results.get(targetFieldName);
            if (targetFieldValue instanceof Computable) {
                Computable computable = (Computable) targetFieldValue;
                resultMap.put(targetFieldName.getValue(), computable.getResult());
            }else {
                resultMap.put(targetFieldName.getValue(), targetFieldValue);
            }
        }
        
        return resultMap;
    }

    // 获取模型需要的特征名称
    public List<String> getFeatureNames() {
        List<String> featureNames = new ArrayList<String>();

        List<InputField> inputFields = modelEvaluator.getInputFields();

        for (InputField inputField : inputFields) {
            featureNames.add(inputField.getName().toString());
        }
        return featureNames;
    }

    // 获取目标字段名称
    public String getTargetName() {
        return modelEvaluator.getTargetFields().get(0).getName().toString();
    }
    
    // // 使用模型生成概率分布
    // private ProbabilityDistribution getProbabilityDistribution(Map<FieldName, ?> arguments) {
    //     Map<FieldName, ?> evaluateResult = modelEvaluator.evaluate(arguments);

    //     FieldName fieldName = new FieldName(getTargetName());

    //     return (ProbabilityDistribution) evaluateResult.get(fieldName);

    // }

    // // 预测不同分类的概率
    // public ValueMap<String, Number> predictProba(Map<FieldName, Number> arguments) {
    //     ProbabilityDistribution probabilityDistribution = getProbabilityDistribution(arguments);
    //     return probabilityDistribution.getValues();
    // }

    // // 预测结果分类
    // public Object predict(Map<FieldName, ?> arguments) {
    //     ProbabilityDistribution probabilityDistribution = getProbabilityDistribution(arguments);

    //     return probabilityDistribution.getPrediction();
    // }

    public static void main(String[] args) {
        // model route
        PMMLModel model = new PMMLModel("src\\main\\java\\com\\capstone\\eta\\model\\MLP_0128.pmml");

        List<String> featureNames = model.getFeatureNames();
        System.out.println("feature: " + featureNames);
        System.out.println("feature number: " + featureNames.size());
        System.out.println(model.getTargetName());
        // // 构建待预测数据
        // Map<FieldName, Number> waitPreSample = new HashMap<>(); // #这里的key一定要对应python中的列名
        // waitPreSample.put(new FieldName("sepal length (cm)"), 10);
        // waitPreSample.put(new FieldName("sepal width (cm)"), 1);
        // waitPreSample.put(new FieldName("petal length (cm)"), 3);
        // waitPreSample.put(new FieldName("petal width (cm)"), 2);
        // for (int i = 0; i < 506; i++) {
        //     waitPreSample.put(new FieldName("" + i), 1);
        // }

        // System.out.println("waitPreSample predict result: " + clf.predict(waitPreSample).toString());
        // System.out.println("waitPreSample predictProba result: " + clf.predictProba(waitPreSample).toString());

    }

}