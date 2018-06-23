package com.rishiqing.qywx.service.model.corp;

import java.util.Date;

public class CorpSuiteVO {
    private Long id;

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
        return "CorpSuiteVO{" +
                "id=" + id +
                ", corpId='" + corpId + '\'' +
                ", suiteKey='" + suiteKey + '\'' +
                ", permanentCode='" + permanentCode + '\'' +
                ", authUserId='" + authUserId + '\'' +
                ", authUserName='" + authUserName + '\'' +
                ", authUserAvatar='" + authUserAvatar + '\'' +
                '}';
    }
}
