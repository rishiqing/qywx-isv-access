package com.rishiqing.qywx.service.event.message;

import java.io.Serializable;

public class CorpSuiteMessage implements Serializable {
    private String suiteKey;
    private String corpId;

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

    @Override
    public String toString() {
        return "CorpSuiteMessage{" +
                "suiteKey='" + suiteKey + '\'' +
                ", corpId='" + corpId + '\'' +
                '}';
    }
}
