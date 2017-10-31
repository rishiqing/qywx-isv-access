package com.rishiqing.qywx.dao.model.isv;

import java.util.Date;

public class SuiteTokenDO {
    private Long id;
    private Date dateCreated;
    private Date lastUpdated;

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

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
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
        return "SuiteTokenDO{" +
                "id=" + id +
                ", dateCreated=" + dateCreated +
                ", lastUpdated=" + lastUpdated +
                ", suiteKey='" + suiteKey + '\'' +
                ", suiteToken='" + suiteToken + '\'' +
                ", expiresIn='" + expiresIn + '\'' +
                ", tokenUpdateTime=" + tokenUpdateTime +
                '}';
    }
}
