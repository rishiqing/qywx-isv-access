package com.rishiqing.qywx.dao.model.corp;

import java.util.Date;

public class CorpSuiteDO {
    private Long id;
    private Date dateCreated;
    private Date lastUpdated;

    private String corpId;
    private String suiteKey;
    private String permanentCode;

    //  授权开通应用的管理员的基本信息
    private String authUserId;
    private String authUserName;
    private String authUserAvatar;

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

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getSuiteKey() {
        return suiteKey;
    }

    public void setSuiteKey(String suiteKey) {
        this.suiteKey = suiteKey;
    }

    public String getPermanentCode() {
        return permanentCode;
    }

    public void setPermanentCode(String permanentCode) {
        this.permanentCode = permanentCode;
    }

    public String getAuthUserId() {
        return authUserId;
    }

    public void setAuthUserId(String authUserId) {
        this.authUserId = authUserId;
    }

    public String getAuthUserName() {
        return authUserName;
    }

    public void setAuthUserName(String authUserName) {
        this.authUserName = authUserName;
    }

    public String getAuthUserAvatar() {
        return authUserAvatar;
    }

    public void setAuthUserAvatar(String authUserAvatar) {
        this.authUserAvatar = authUserAvatar;
    }

    @Override
    public String toString() {
        return "CorpSuiteDO{" +
                "id=" + id +
                ", dateCreated=" + dateCreated +
                ", lastUpdated=" + lastUpdated +
                ", corpId='" + corpId + '\'' +
                ", suiteKey='" + suiteKey + '\'' +
                ", permanentCode='" + permanentCode + '\'' +
                ", authUserId='" + authUserId + '\'' +
                ", authUserName='" + authUserName + '\'' +
                ", authUserAvatar='" + authUserAvatar + '\'' +
                '}';
    }
}
