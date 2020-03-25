package com.yaochen.tester.day01;

import com.yaochen.tester.tools.Httputil;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class RecaseV2 {
    @Test(dataProvider = "datas")
    public void test1() {
        String url = "http://132.232.44.158:8080/morning/searchGoods";
        Map<String, String> data = new HashMap<String, String>();
        data.put("queryGoods.search", "");
        data.put("queryGoods.condition", "");
        data.put("pageInfo.currentPage", "2");
        data.put("pageInfo.totalPage", "2");
        String AA = Httputil.doPost(url, data);
        System.out.println(AA);

    }
//    @DataProvider
//    public Object [] []datas(){
//        Object [] []datas =
//        return datas;
//    }
}