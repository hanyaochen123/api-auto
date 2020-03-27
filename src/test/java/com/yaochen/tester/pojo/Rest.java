package com.yaochen.tester.pojo;

public class Rest {
    private String apiId;
    private String apiName;
    private String Type;
    private String Url;

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    @Override
    public String toString() {
        return "Rest{" +
                "apiId='" + apiId + '\'' +
                ", apiName='" + apiName + '\'' +
                ", Type='" + Type + '\'' +
                ", Url='" + Url + '\'' +
                '}';
    }
}
