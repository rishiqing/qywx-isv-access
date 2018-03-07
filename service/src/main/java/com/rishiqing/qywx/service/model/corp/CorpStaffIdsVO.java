package com.rishiqing.qywx.service.model.corp;

/**
 * Created with IntelliJ IDEA.
 * User: user 毛文强
 * Date: 2017/11/20
 * Time: 14:27
 * To change this template use File | Settings | File Templates.
 */
public class CorpStaffIdsVO {
    private String userId;

    private String rsqUserId;  //日事清中的用户id

    private String avatar;
    private String name;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRsqUserId() {
        return rsqUserId;
    }

    public void setRsqUserId(String rsqUserId) {
        this.rsqUserId = rsqUserId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CorpStaffIdsVO{" +
                "userId='" + userId + '\'' +
                ", rsqUserId='" + rsqUserId + '\'' +
                ", avatar='" + avatar + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
