package com.yaochen.tester.tools;

import com.yaochen.tester.pojo.Rest;

import java.util.ArrayList;
import java.util.List;

public class RestUtil {
    public static List<Rest> rests=new ArrayList<Rest>();
    static{
        ExcelUtil.load("src/main/resources/casesV1.xlsx","接口信息",Rest.class);
            }
    public static String getUrlByApiId(String apiId){
        for (Rest rest:rests){
            if (rest.getApiId().equals(apiId)){
                return rest.getUrl();
            }
        }
        return "";
    }
    public static String getTypeByApiId(String apiId){
        for (Rest rest:rests){
            if (rest.getApiId().equals(apiId)){
                return rest.getType();
            }
        }
        return "";

    }

    public static void main(String[] args) {
        for (Rest rest:rests){
            System.out.println(rest);

        }

    }
}
