package com.rishiqing.qywx.model;

public class AppVO {
    private Long id;

    private String appId;
    private String suiteKey;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSuiteKey() {
        return suiteKey;
    }

    public void setSuiteKey(String suiteKey) {
        this.suiteKey = suiteKey;
    }

    @Override
    public String toString() {
        return "AppVO{" +
                "id=" + id +
                ", appId='" + appId + '\'' +
                ", suiteKey='" + suiteKey + '\'' +
                '}';
    }
}
