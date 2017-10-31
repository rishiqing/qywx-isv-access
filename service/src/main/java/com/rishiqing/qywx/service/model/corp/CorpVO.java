package com.rishiqing.qywx.service.model.corp;

import java.util.Date;

public class CorpVO {
    private Long id;

    private String corpId;
    private String corpName;
    private String corpType;
    private String corpSquareLogoUrl;
    private Long corpUserMax;
    private Long corpAgentMax;
    private String corpFullName;
    private Long verifiedEndTime;
    private Long subjectType;
    private String corpWxqrcode;
    private String authEmail;
    private String authMobile;
    private String authUserId;
    private String authName;
    private String authAvatar;
    private Boolean authCanceled;

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

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public String getCorpType() {
        return corpType;
    }

    public void setCorpType(String corpType) {
        this.corpType = corpType;
    }

    public String getCorpSquareLogoUrl() {
        return corpSquareLogoUrl;
    }

    public void setCorpSquareLogoUrl(String corpSquareLogoUrl) {
        this.corpSquareLogoUrl = corpSquareLogoUrl;
    }

    public Long getCorpUserMax() {
        return corpUserMax;
    }

    public void setCorpUserMax(Long corpUserMax) {
        this.corpUserMax = corpUserMax;
    }

    public Long getCorpAgentMax() {
        return corpAgentMax;
    }

    public void setCorpAgentMax(Long corpAgentMax) {
        this.corpAgentMax = corpAgentMax;
    }

    public String getCorpFullName() {
        return corpFullName;
    }

    public void setCorpFullName(String corpFullName) {
        this.corpFullName = corpFullName;
    }

    public Long getVerifiedEndTime() {
        return verifiedEndTime;
    }

    public void setVerifiedEndTime(Long verifiedEndTime) {
        this.verifiedEndTime = verifiedEndTime;
    }

    public Long getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(Long subjectType) {
        this.subjectType = subjectType;
    }

    public String getCorpWxqrcode() {
        return corpWxqrcode;
    }

    public void setCorpWxqrcode(String corpWxqrcode) {
        this.corpWxqrcode = corpWxqrcode;
    }

    public String getAuthEmail() {
        return authEmail;
    }

    public void setAuthEmail(String authEmail) {
        this.authEmail = authEmail;
    }

    public String getAuthMobile() {
        return authMobile;
    }

    public void setAuthMobile(String authMobile) {
        this.authMobile = authMobile;
    }

    public String getAuthUserId() {
        return authUserId;
    }

    public void setAuthUserId(String authUserId) {
        this.authUserId = authUserId;
    }

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }

    public String getAuthAvatar() {
        return authAvatar;
    }

    public void setAuthAvatar(String authAvatar) {
        this.authAvatar = authAvatar;
    }

    public Boolean getAuthCanceled() {
        return authCanceled;
    }

    public void setAuthCanceled(Boolean authCanceled) {
        this.authCanceled = authCanceled;
    }

    @Override
    public String toString() {
        return "CorpVO{" +
                "id=" + id +
                ", corpId='" + corpId + '\'' +
                ", corpName='" + corpName + '\'' +
                ", corpType='" + corpType + '\'' +
                ", corpSquareLogoUrl='" + corpSquareLogoUrl + '\'' +
                ", corpUserMax=" + corpUserMax +
                ", corpAgentMax=" + corpAgentMax +
                ", corpFullName='" + corpFullName + '\'' +
                ", verifiedEndTime=" + verifiedEndTime +
                ", subjectType=" + subjectType +
                ", corpWxqrcode='" + corpWxqrcode + '\'' +
                ", authEmail='" + authEmail + '\'' +
                ", authMobile='" + authMobile + '\'' +
                ", authUserId='" + authUserId + '\'' +
                ", authName='" + authName + '\'' +
                ", authAvatar='" + authAvatar + '\'' +
                '}';
    }
}
