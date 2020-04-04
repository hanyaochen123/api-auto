package com.yaochen.tester.tools;

import com.yaochen.tester.pojo.Variable;
import org.apache.log4j.Logger;

import java.util.*;
//参数化
public class VariableUtil {
    public static Logger logger=Logger.getLogger(VariableUtil.class);
    public static Map<String,String> variableNameAndValuesMap=new HashMap<String, String>();
    public static List<Variable> variables=new ArrayList<Variable>();
    static {
        List<Variable> list=ExcelUtil.load("src/main/resources/casesV1.xlsx","变量",Variable.class);
        variables.addAll(list);
        loadVariablesToMap();
    }

    private static void loadVariablesToMap() {
        for (Variable variable:variables){
            String name=variable.getName();
            String value=variable.getValue();
            logger.info("变量名:"+name+"变量值:"+value);
            variableNameAndValuesMap.put(name,value);

        }
    }
    //遍历变量集合 将对应的变量名和变量值保存到map
    public static String replaceVariables(String parameter) {
        Set<String> variableNames=variableNameAndValuesMap.keySet();
        for (String variableName:variableNames){
            if (parameter.contains(variableName)){
                parameter=parameter.replace(variableName,variableNameAndValuesMap.get(variableName));

            }

        }
        return parameter;
    }


}
