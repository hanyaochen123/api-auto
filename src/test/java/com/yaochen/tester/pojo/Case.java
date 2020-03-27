package com.yaochen.tester.pojo;

public class Case {
    private String caseId;
    private String apiId;
    private String desc;
    private String params;
    private String expectedResponseData;
    private String actualResponseData;
    private String PreValidateSql;
    private String PreValidateResult;
    private String AfterValidateSql;
    private String AfterValidateResult;

    public String getPreValidateSql() {
        return PreValidateSql;
    }

    public void setPreValidateSql(String preValidateSql) {
        PreValidateSql = preValidateSql;
    }

    public String getPreValidateResult() {
        return PreValidateResult;
    }

    public void setPreValidateResult(String preValidateResult) {
        PreValidateResult = preValidateResult;
    }

    public String getAfterValidateSql() {
        return AfterValidateSql;
    }

    public void setAfterValidateSql(String afterValidateSql) {
        AfterValidateSql = afterValidateSql;
    }

    public String getAfterValidateResult() {
        return AfterValidateResult;
    }

    public void setAfterValidateResult(String afterValidateResult) {
        AfterValidateResult = afterValidateResult;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getExpectedResponseData() {
        return expectedResponseData;
    }

    public void setExpectedResponseData(String expectedResponseData) {
        this.expectedResponseData = expectedResponseData;
    }

    public String getActualResponseData() {
        return actualResponseData;
    }

    public void setActualResponseData(String actualResponseData) {
        this.actualResponseData = actualResponseData;
    }

    @Override
    public String toString() {
        return "Case{" +
                "caseId='" + caseId + '\'' +
                ", apiId='" + apiId + '\'' +
                ", desc='" + desc + '\'' +
                ", params='" + params + '\'' +
                ", expectedResponseData='" + expectedResponseData + '\'' +
                ", actualResponseData='" + actualResponseData + '\'' +
                ", PreValidateSql='" + PreValidateSql + '\'' +
                ", PreValidateResult='" + PreValidateResult + '\'' +
                ", AfterValidateSql='" + AfterValidateSql + '\'' +
                ", AfterValidateResult='" + AfterValidateResult + '\'' +
                '}';
    }
}

