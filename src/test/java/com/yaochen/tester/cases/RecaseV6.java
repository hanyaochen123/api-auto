package com.yaochen.tester.cases;
import com.yaochen.tester.tools.*;
import org.testng.annotations.DataProvider;
public class RecaseV6 extends BaseProcessor {


    @DataProvider
    public Object [] []datas(){
        Object[][]datas= CaseUtil.getCaseApiId("1",cellNames);
        return datas;
    }


}

