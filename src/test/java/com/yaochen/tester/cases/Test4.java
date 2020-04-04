package com.yaochen.tester.cases;

import com.yaochen.tester.tools.CaseUtil;
import org.testng.annotations.DataProvider;

public class Test4 extends BaseProcessor{
    @DataProvider
    public Object[][] datas() {
        Object[][] datas = CaseUtil.getCaseApiId("4", cellNames);
        return datas;
    }
}
