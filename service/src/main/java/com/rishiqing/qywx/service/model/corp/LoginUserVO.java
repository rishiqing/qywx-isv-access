package com.rishiqing.qywx.service.model.corp;

public class LoginUserVO {
    private Long id;

    private String corpId;
    private String userId;
    private String openId;
    private String deviceId;
    private String userTicket;
    private Long expiresIn;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getUserTicket() {
        return userTicket;
    }

    public void setUserTicket(String userTicket) {
        this.userTicket = userTicket;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    @Override
    public String toString() {
        return "LoginUserVO{" +
                "id=" + id +
                ", corpId='" + corpId + '\'' +
                ", userId='" + userId + '\'' +
                ", openId='" + openId + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", userTicket='" + userTicket + '\'' +
                ", expiresIn=" + expiresIn +
                '}';
    }
}
