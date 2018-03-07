package com.rishiqing.common.model;

/**
 * @author Wallace Mao
 * Date: 2018-02-08 15:57
 * 日事清的user对象，各属性除了特殊标注，均与日事清中保持一致
 */
public class RsqCommonUserVO {
    private Long id;
    private String username;
    private String password;
    private String realName;
    private Long teamId;
    private String department;
    private String unionId;
    private String outerId;
    private String fromClient;

    private String corpId;  //本地的corpId
    private String userId;  //本地的userId
    private Boolean isAdmin;  //本地isAdmin

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getOuterId() {
        return outerId;
    }

    public void setOuterId(String outerId) {
        this.outerId = outerId;
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

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public String getFromClient() {
        return fromClient;
    }

    public void setFromClient(String fromClient) {
        this.fromClient = fromClient;
    }

    @Override
    public String toString() {
        return "RsqCommonUserVO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", realName='" + realName + '\'' +
                ", teamId=" + teamId +
                ", department='" + department + '\'' +
                ", unionId='" + unionId + '\'' +
                ", outerId='" + outerId + '\'' +
                ", fromClient='" + fromClient + '\'' +
                ", corpId='" + corpId + '\'' +
                ", userId='" + userId + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }
}
