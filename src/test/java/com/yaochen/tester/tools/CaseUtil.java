package com.yaochen.tester.tools;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class CaseUtil {
    public static List<Case> cases = new ArrayList<Case>();

    static{
        ExcelUtil.load("src/main/resources/casesV1.xlsx","login",Case.class);
    }

    public static Object[][] getCaseApiId(String apiId, String[] cellNames) {
        Class<Case>clazz=Case.class;
        //保存指定接口编号的case对象的集合
        ArrayList<Case> caList = new ArrayList<Case>();
        for (Case cs : cases) {
            if (cs.getApiId().equals(apiId)) {
                caList.add(cs);
            }
        }
        Object[][] datas = new Object[caList.size()][cellNames.length];
        for (int i = 0; i < caList.size(); i++) {
            Case cs = caList.get(i);
            for (int j = 0; j < cellNames.length; j++) {
                String methodName="get"+cellNames[j];
                Method method= null;
                try {
                    method = clazz.getMethod(methodName);
                    String value= (String) method.invoke(cs);
                    datas[i][j] = value;
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        return datas;
    }

    public static void main(String[] args) {
        String [] cellNames={"Params"};
        Object[][] datas=getCaseApiId("1",cellNames);
        for (Object[] objects:datas){
            for (Object object:objects)
            System.out.println(object);
        }


    }
}
