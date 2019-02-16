package com.rishiqing.qywx.service.model.corp;

/**
 * 用户付费版本的vo对象
 * @author Wallace Mao
 * Date: 2019-02-15 17:39
 */
public class CorpEditionVO {
    private Long agentId;
    private String editionId;
    private String editionName;
    private Long appStatus;
    private Long userLimit;
    private Long expiredTime;

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public String getEditionId() {
        return editionId;
    }

    public void setEditionId(String editionId) {
        this.editionId = editionId;
    }

    public String getEditionName() {
        return editionName;
    }

    public void setEditionName(String editionName) {
        this.editionName = editionName;
    }

    public Long getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(Long appStatus) {
        this.appStatus = appStatus;
    }

    public Long getUserLimit() {
        return userLimit;
    }

    public void setUserLimit(Long userLimit) {
        this.userLimit = userLimit;
    }

    public Long getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Long expiredTime) {
        this.expiredTime = expiredTime;
    }

    @Override
    public String toString() {
        return "CorpEditionVO{" +
                "agentId=" + agentId +
                ", editionId='" + editionId + '\'' +
                ", editionName='" + editionName + '\'' +
                ", appStatus=" + appStatus +
                ", userLimit=" + userLimit +
                ", expiredTime=" + expiredTime +
                '}';
    }
}
