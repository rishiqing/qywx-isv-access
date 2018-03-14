package com.rishiqing.qywx.service.model.isv;

import java.util.Date;

public class SuitePreAuthCodeVO {
    private Long id;

    private String suiteKey;
    private String suitePreAuthCode;
    private Long expiresIn;
    private Date codeUpdateTime;

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

    public String getSuitePreAuthCode() {
        return suitePreAuthCode;
    }

    public void setSuitePreAuthCode(String suitePreAuthCode) {
        this.suitePreAuthCode = suitePreAuthCode;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public Date getCodeUpdateTime() {
        return codeUpdateTime;
    }

    public void setCodeUpdateTime(Date codeUpdateTime) {
        this.codeUpdateTime = codeUpdateTime;
    }

    @Override
    public String toString() {
        return "SuitePreAuthCodeVO{" +
                "id=" + id +
                ", suiteKey='" + suiteKey + '\'' +
                ", suitePreAuthCode='" + suitePreAuthCode + '\'' +
                ", expiresIn=" + expiresIn +
                ", codeUpdateTime=" + codeUpdateTime +
                '}';
    }
}
