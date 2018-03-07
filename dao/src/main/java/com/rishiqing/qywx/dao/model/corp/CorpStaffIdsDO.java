package com.rishiqing.qywx.dao.model.corp;

/**
 * 用于记录用户id映射关系的staff简化对象
 * Created by Wallace on 17-11-20
 */
public class CorpStaffIdsDO {
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
        return "CorpStaffIdsDO{" +
                "userId='" + userId + '\'' +
                ", rsqUserId='" + rsqUserId + '\'' +
                ", avatar='" + avatar + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
