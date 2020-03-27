package com.yaochen.tester.cases;

import com.alibaba.fastjson.JSONObject;
import com.yaochen.tester.pojo.WriteBackData;
import com.yaochen.tester.tools.*;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.util.Map;

/*

 */
public class BaseProcessor {
    public String[] cellNames = {"CaseId","ApiId", "Params","ExpectedResponseData","PreValidateSql","AfterValidateSql"};
    @Test(dataProvider = "datas")
    public void test1(String caseId,String apiId, String parameter,String expectedResponseData, String preValidateSql,String afterValidateSql) {
        if(preValidateSql!=null&&preValidateSql.trim().length()>0){
            String searchresult= DBCheckUtil.doQuery(preValidateSql);
            ExcelUtil.writeBackDatas.add(new WriteBackData("login",caseId,"PreValidateResult",searchresult));
        }
        //根据apiId来判断是哪个url
        String url = RestUtil.getUrlByApiId(apiId);
        //根据apiID来判断是哪个请求方式
        String type = RestUtil.getTypeByApiId(apiId);
        //根据Alibaba的json解析来解析参数
        Map<String, String> params = (Map<String, String>) JSONObject.parse(parameter);
        //根据封装的请求方法来调用，拿到接口响应报文
        String actualResponseData = Httputil.doService(url, type, params);
        //判断两个结果是否一致
        actualResponseData= AssertUtil.assertEquals(actualResponseData,expectedResponseData);
        System.out.println(actualResponseData);
        ExcelUtil.writeBackDatas.add(new WriteBackData("login",caseId,"ActualResponseData",actualResponseData));
        if (afterValidateSql!=null&&afterValidateSql.trim().length()>0){
            String verificationresult= DBCheckUtil.doQuery(afterValidateSql);
            ExcelUtil.writeBackDatas.add(new WriteBackData("login",caseId,"AfterValidateResult",verificationresult));
        }
    }
    @AfterTest
    public void batchWriteBackDatas(){
        ExcelUtil.batchWriteBackDatas("src/main/resources/casesV1.xlsx");

    }

}
