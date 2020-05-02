package com.yaochen.tester.cases;

import com.yaochen.tester.tools.CaseUtil;
import org.testng.annotations.DataProvider;

public class Wzzlist extends BaseProcessor{
    @DataProvider
    public Object[][] datas() {
        Object[][] datas = CaseUtil.getCaseApiId("7", cellNames);
        return datas;
    }
}
