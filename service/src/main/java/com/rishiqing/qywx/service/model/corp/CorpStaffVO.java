package com.rishiqing.qywx.service.model.corp;

import java.util.Date;

public class CorpStaffVO {
    private Long id;

    private String corpId;
    private String userId;
    private String name;
    private String department;
    private String orderInDepts;
    private String isLeaderInDepts;
    private String position;
    private String mobile;
    private String gender;
    private String email;
    private String avatar;
    private String tel;
    private String englishName;
    private Long status;
    private String extattr;

    private String rsqUserId;
    private String rsqUsername;
    private String rsqPassword;
    private String rsqLoginToken;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getOrderInDepts() {
        return orderInDepts;
    }

    public void setOrderInDepts(String orderInDepts) {
        this.orderInDepts = orderInDepts;
    }

    public String getIsLeaderInDepts() {
        return isLeaderInDepts;
    }

    public void setIsLeaderInDepts(String isLeaderInDepts) {
        this.isLeaderInDepts = isLeaderInDepts;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getExtattr() {
        return extattr;
    }

    public void setExtattr(String extattr) {
        this.extattr = extattr;
    }

    public String getRsqUserId() {
        return rsqUserId;
    }

    public void setRsqUserId(String rsqUserId) {
        this.rsqUserId = rsqUserId;
    }

    public String getRsqUsername() {
        return rsqUsername;
    }

    public void setRsqUsername(String rsqUsername) {
        this.rsqUsername = rsqUsername;
    }

    public String getRsqPassword() {
        return rsqPassword;
    }

    public void setRsqPassword(String rsqPassword) {
        this.rsqPassword = rsqPassword;
    }

    public String getRsqLoginToken() {
        return rsqLoginToken;
    }

    public void setRsqLoginToken(String rsqLoginToken) {
        this.rsqLoginToken = rsqLoginToken;
    }

    @Override
    public String toString() {
        return "CorpStaffVO{" +
                "id=" + id +
                ", corpId='" + corpId + '\'' +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", orderInDepts='" + orderInDepts + '\'' +
                ", isLeaderInDepts='" + isLeaderInDepts + '\'' +
                ", position='" + position + '\'' +
                ", mobile='" + mobile + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                ", tel='" + tel + '\'' +
                ", englishName='" + englishName + '\'' +
                ", status=" + status +
                ", extattr='" + extattr + '\'' +
                ", rsqUserId='" + rsqUserId + '\'' +
                ", rsqUsername='" + rsqUsername + '\'' +
                ", rsqPassword='" + rsqPassword + '\'' +
                ", rsqLoginToken='" + rsqLoginToken + '\'' +
                '}';
    }
}
