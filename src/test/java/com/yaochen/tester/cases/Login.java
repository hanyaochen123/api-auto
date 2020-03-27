package com.yaochen.tester.cases;

import com.yaochen.tester.tools.*;
import org.testng.annotations.DataProvider;

public class Login extends BaseProcessor{
    @DataProvider
    public Object[][] datas() {
            Object[][] datas = CaseUtil.getCaseApiId("2", cellNames);
            return datas;
    }
}

