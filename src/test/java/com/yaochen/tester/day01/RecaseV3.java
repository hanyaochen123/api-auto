package com.yaochen.tester.day01;

import com.yaochen.tester.tools.ExcelUtil;
import com.yaochen.tester.tools.Httputil;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class RecaseV3 {
    @Test(dataProvider = "datas")
    public void test1(String search,String condition) {
        String url = "http://132.232.44.158:8080/morning/searchGoods";
        Map<String, String> data = new HashMap<String, String>();
        data.put("search", search);
        data.put("condition", condition);
        String AA = Httputil.doPost(url, data);
        System.out.println(AA);
   }
//    @DataProvider
//    public Object [] []datas(){
////        Object[][]datas=ExcelUtil.datas("src/main/resources/casesV1.xlsx",1,6,5,6);
//        return datas;
//    }


}
