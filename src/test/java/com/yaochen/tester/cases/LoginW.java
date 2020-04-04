package com.yaochen.tester.cases;

import com.yaochen.tester.tools.CaseUtil;
import org.testng.annotations.DataProvider;

public class LoginW extends BaseProcessor{
    @DataProvider
    public Object[][] datas() {
        Object[][] datas = CaseUtil.getCaseApiId("3", cellNames);
        return datas;
    }
}
