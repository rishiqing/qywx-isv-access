package com.rishiqing.qywx.dao.model.fail;

import java.util.Date;

/**
 * @author Wallace Mao
 * Date: 2018-02-28 15:36
 */
public class FailContactCallbackDO {
    private Long id;
    private Date dateCreated;
    private Date lastUpdated;

    private String suiteKey;
    private String corpId;
    private String failType;
    private String changeType;
    private String failNote;
    private String eventMsg;

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

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public String getFailNote() {
        return failNote;
    }

    public void setFailNote(String failNote) {
        this.failNote = failNote;
    }

    public String getEventMsg() {
        return eventMsg;
    }

    public void setEventMsg(String eventMsg) {
        this.eventMsg = eventMsg;
    }

    @Override
    public String toString() {
        return "FailContactCallbackDO{" +
                "id=" + id +
                ", dateCreated=" + dateCreated +
                ", lastUpdated=" + lastUpdated +
                ", suiteKey='" + suiteKey + '\'' +
                ", corpId='" + corpId + '\'' +
                ", failType='" + failType + '\'' +
                ", changeType='" + changeType + '\'' +
                ", failNote='" + failNote + '\'' +
                ", eventMsg='" + eventMsg + '\'' +
                '}';
    }
}
