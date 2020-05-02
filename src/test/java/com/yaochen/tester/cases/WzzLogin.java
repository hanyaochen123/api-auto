package com.yaochen.tester.cases;

import com.yaochen.tester.tools.CaseUtil;
import org.testng.annotations.DataProvider;

public class WzzLogin extends BaseProcessor{
    @DataProvider
    public Object[][] datas() {
        Object[][] datas = CaseUtil.getCaseApiId("6", cellNames);
        return datas;
    }

}
