package com.rishiqing.qywx.service.model.isv;

import java.util.Date;

public class SuiteTokenVO {
    private Long id;

    private String suiteKey;
    private String suiteToken;
    private Long expiresIn;
    private Date tokenUpdateTime;

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

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public Date getTokenUpdateTime() {
        return tokenUpdateTime;
    }

    public void setTokenUpdateTime(Date tokenUpdateTime) {
        this.tokenUpdateTime = tokenUpdateTime;
    }

    @Override
    public String toString() {
        return "SuiteTokenVO{" +
                "id=" + id +
                ", suiteKey='" + suiteKey + '\'' +
                ", suiteToken='" + suiteToken + '\'' +
                ", expiresIn='" + expiresIn + '\'' +
                ", tokenUpdateTime=" + tokenUpdateTime +
                '}';
    }
}
