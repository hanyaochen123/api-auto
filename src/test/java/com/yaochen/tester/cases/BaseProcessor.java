package com.yaochen.tester.cases;

import com.alibaba.fastjson.JSONObject;
import com.yaochen.tester.pojo.WriteBackData;
import com.yaochen.tester.tools.*;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import java.util.Map;

/*

 */
public class BaseProcessor {
    public Logger logger=Logger.getLogger(BaseProcessor.class);
    public String[] cellNames = {"CaseId","ApiId", "Params","ExpectedResponseData","PreValidateSql","AfterValidateSql"};
    @Test(dataProvider = "datas")
    public void test1(String caseId,String apiId, String parameter,String expectedResponseData, String preValidateSql,String afterValidateSql) {
        logger.info("调用接口前的数据库验证:");
        if(preValidateSql!=null&&preValidateSql.trim().length()>0){
            String searchresult= DBCheckUtil.doQuery(preValidateSql);
            ExcelUtil.writeBackDatas.add(new WriteBackData("login",caseId,"PreValidateResult",searchresult));
        }
        logger.info("根据接口编号["+apiId+"]获取接口请求地址");
        //根据apiId来判断是哪个url
        String url = RestUtil.getUrlByApiId(apiId);
        logger.info("根据接口编号获取接口提交类型");
        //根据apiID来判断是哪个请求方式
        String type = RestUtil.getTypeByApiId(apiId);
        logger.info("替换变量"+parameter);
        parameter=VariableUtil.replaceVariables(parameter);
        //根据Alibaba的json解析来解析参数
        Map<String, Object> params = (Map<String, Object>) JSONObject.parse(parameter);
        logger.info("开始调用接口");
        //根据封装的请求方法来调用，拿到接口响应报文
        String actualResponseData = Httputil.doService(url, type, params);
        logger.info("请求结果是"+actualResponseData);
        //判断两个结果是否一致
        actualResponseData= AssertUtil.assertEquals(actualResponseData,expectedResponseData);
        ExcelUtil.writeBackDatas.add(new WriteBackData("login",caseId,"ActualResponseData",actualResponseData));
        logger.info("接口调用后的数据验证");
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
