package com.rishiqing.qywx.dao.model.isv;

import java.util.Date;

/**
 * 套件对象
 */
public class IsvDO {
    private Long id;
    private Date dateCreated;
    private Date lastUpdated;

    private String providerSecret;  // isv的唯一secret
    private String encodingAesKey;  // 回调信息加解密参数
    private String token;  //  已填写用于生成签名和校验毁掉请求的合法性
    private String corpId;  // isv所在公司的corpId
    private String providerAccessToken;
    private Long expiresIn;
    private Date providerTokenUpdateTime;  // providerAccessToken的更新时间

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

    public String getProviderSecret() {
        return providerSecret;
    }

    public void setProviderSecret(String providerSecret) {
        this.providerSecret = providerSecret;
    }

    public String getEncodingAesKey() {
        return encodingAesKey;
    }

    public void setEncodingAesKey(String encodingAesKey) {
        this.encodingAesKey = encodingAesKey;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getProviderAccessToken() {
        return providerAccessToken;
    }

    public void setProviderAccessToken(String providerAccessToken) {
        this.providerAccessToken = providerAccessToken;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public Date getProviderTokenUpdateTime() {
        return providerTokenUpdateTime;
    }

    public void setProviderTokenUpdateTime(Date providerTokenUpdateTime) {
        this.providerTokenUpdateTime = providerTokenUpdateTime;
    }

    @Override
    public String toString() {
        return "IsvDO{" +
                "id=" + id +
                ", dateCreated=" + dateCreated +
                ", lastUpdated=" + lastUpdated +
                ", providerSecret='" + providerSecret + '\'' +
                ", encodingAesKey='" + encodingAesKey + '\'' +
                ", token='" + token + '\'' +
                ", corpId='" + corpId + '\'' +
                ", providerAccessToken='" + providerAccessToken + '\'' +
                ", expiresIn=" + expiresIn +
                ", providerTokenUpdateTime=" + providerTokenUpdateTime +
                '}';
    }
}
