package com.yaochen.tester.tools;

import com.alibaba.fastjson.JSONObject;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.util.Map;

/*

 */
public class BaseProcessor {
    @Test(dataProvider = "datas")
    public void test1(String caseId,String apiId, String parameter) {
        //根据apiId来判断是哪个url
        String url = RestUtil.getUrlByApiId(apiId);
        //根据apiID来判断是哪个请求方式
        String type = RestUtil.getTypeByApiId(apiId);
        //根据Alibaba的json解析来解析参数
        Map<String, String> params = (Map<String, String>) JSONObject.parse(parameter);
        //根据封装的请求方法来调用，拿到接口响应报文
        String result = Httputil.doService(url, type, params);
        System.out.println(result);
        WriteBackData writeBackData=new WriteBackData("login",caseId,"ActualResponseData",result);
        ExcelUtil.writeBackDatas.add(writeBackData);
    }
    @AfterTest
    public void batchWriteBackDatas(){
        ExcelUtil.batchWriteBackDatas("src/main/resources/casesV1.xlsx");

    }

}
