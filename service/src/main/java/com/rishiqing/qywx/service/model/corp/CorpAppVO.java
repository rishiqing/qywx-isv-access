package com.rishiqing.qywx.service.model.corp;

import java.util.Date;

public class CorpAppVO {
    private Long id;

    private Long agentId;
    private String name;
    private String roundLogoUrl;
    private String squareLogoUrl;
    private String suiteKey;
    private Long appId;
    private String corpId;

    private CorpAuthPrivilegeVO corpAuthPrivilegeVO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoundLogoUrl() {
        return roundLogoUrl;
    }

    public void setRoundLogoUrl(String roundLogoUrl) {
        this.roundLogoUrl = roundLogoUrl;
    }

    public String getSquareLogoUrl() {
        return squareLogoUrl;
    }

    public void setSquareLogoUrl(String squareLogoUrl) {
        this.squareLogoUrl = squareLogoUrl;
    }

    public String getSuiteKey() {
        return suiteKey;
    }

    public void setSuiteKey(String suiteKey) {
        this.suiteKey = suiteKey;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public CorpAuthPrivilegeVO getCorpAuthPrivilegeVO() {
        return corpAuthPrivilegeVO;
    }

    public void setCorpAuthPrivilegeVO(CorpAuthPrivilegeVO corpAuthPrivilegeVO) {
        this.corpAuthPrivilegeVO = corpAuthPrivilegeVO;
    }

    @Override
    public String toString() {
        return "CorpAppVO{" +
                "id=" + id +
                ", agentId=" + agentId +
                ", name='" + name + '\'' +
                ", roundLogoUrl='" + roundLogoUrl + '\'' +
                ", squareLogoUrl='" + squareLogoUrl + '\'' +
                ", suiteKey='" + suiteKey + '\'' +
                ", appId=" + appId +
                ", corpId='" + corpId + '\'' +
                '}';
    }
}
