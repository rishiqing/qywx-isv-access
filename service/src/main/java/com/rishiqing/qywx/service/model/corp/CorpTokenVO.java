package com.rishiqing.qywx.service.model.corp;

import java.util.Date;

public class CorpTokenVO {
    private Long id;

    private String suiteKey;
    private String corpId;
    private String corpToken;
    private Long expiresIn;

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

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getCorpToken() {
        return corpToken;
    }

    public void setCorpToken(String corpToken) {
        this.corpToken = corpToken;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    @Override
    public String toString() {
        return "CorpTokenVO{" +
                "id=" + id +
                ", suiteKey='" + suiteKey + '\'' +
                ", corpId='" + corpId + '\'' +
                ", corpToken='" + corpToken + '\'' +
                ", expiresIn=" + expiresIn +
                '}';
    }
}
