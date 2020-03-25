package com.yaochen.tester.day01;

import com.alibaba.fastjson.JSONObject;
import com.yaochen.tester.tools.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Map;

public class Login extends BaseProcessor{
    @DataProvider
    public Object[][] datas() {
            String[] cellNames = {"CaseId","ApiId", "Params"};
            Object[][] datas = CaseUtil.getCaseApiId("2", cellNames);
            return datas;
    }
}

