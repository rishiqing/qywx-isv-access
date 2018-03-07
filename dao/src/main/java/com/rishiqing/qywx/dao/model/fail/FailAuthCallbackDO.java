package com.rishiqing.qywx.dao.model.fail;

import java.util.Date;

/**
 * @author Wallace Mao
 * Date: 2018-02-28 15:36
 */
public class FailAuthCallbackDO {
    private Long id;
    private Date dateCreated;
    private Date lastUpdated;

    private String suiteKey;
    private String corpId;
    private String failType;
    private String infoType;
    private String failNote;

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

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getFailType() {
        return failType;
    }

    public void setFailType(String failType) {
        this.failType = failType;
    }

    public String getInfoType() {
        return infoType;
    }

    public void setInfoType(String infoType) {
        this.infoType = infoType;
    }

    public String getFailNote() {
        return failNote;
    }

    public void setFailNote(String failNote) {
        this.failNote = failNote;
    }

    @Override
    public String toString() {
        return "FailAuthCallbackDO{" +
                "id=" + id +
                ", dateCreated=" + dateCreated +
                ", lastUpdated=" + lastUpdated +
                ", suiteKey='" + suiteKey + '\'' +
                ", corpId='" + corpId + '\'' +
                ", failType='" + failType + '\'' +
                ", infoType='" + infoType + '\'' +
                ", failNote='" + failNote + '\'' +
                '}';
    }
}
