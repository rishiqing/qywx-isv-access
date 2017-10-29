package com.rishiqing.qywx.service.model.isv;

public class SuiteTokenVO {
    private Long id;

    private String suiteKey;
    private String suiteToken;
    private String expiredTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSuiteKey() {
        return suiteKey;
    }

    public void setSuiteKey(String suiteKey) {
        this.suiteKey = suiteKey;
    }

    public String getSuiteToken() {
        return suiteToken;
    }

    public void setSuiteToken(String suiteToken) {
        this.suiteToken = suiteToken;
    }

    public String getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(String expiredTime) {
        this.expiredTime = expiredTime;
    }

    @Override
    public String toString() {
        return "SuiteTokenVO{" +
                "id=" + id +
                ", suiteKey='" + suiteKey + '\'' +
                ", suiteToken='" + suiteToken + '\'' +
                ", expiredTime='" + expiredTime + '\'' +
                '}';
    }
}
