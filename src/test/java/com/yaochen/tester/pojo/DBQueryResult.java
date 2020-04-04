package com.yaochen.tester.pojo;

import java.util.Map;

public class DBQueryResult {
    //脚本编号
    private String no;
    //脚本执行查到的数据 保存到map中
    private Map<String,Object>columenLabelAndValues;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public Map<String, Object> getColumenLabelAndValues() {
        return columenLabelAndValues;

    }

    public void setColumenLabelAndValues(Map<String, Object> columenLabelAndValues) {
        this.columenLabelAndValues = columenLabelAndValues;
    }

}
