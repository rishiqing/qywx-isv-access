package com.rishiqing.qywx.service.model.corp;

import java.util.List;

/**
 * 映射企业微信的权限对象
 "level":1,
 "allow_party":[1,2,3],
 "allow_user":["zhansan","lisi"],
 "allow_tag":[1,2,3],
 "extra_party":[4,5,6],
 "extra_user":["wangwu"],
 "extra_tag":[4,5,6]
 * @author Wallace Mao
 * Date: 2018-04-27 21:07
 */
public class CorpAuthPrivilegeVO {
    private Long level;
    private List<Long> allowParty;
    private List<String> allowUser;
    private List<Long> allowTag;
    private List<Long> extraParty;
    private List<String> extraUser;
    private List<Long> extraTag;

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public List<Long> getAllowParty() {
        return allowParty;
    }

    public void setAllowParty(List<Long> allowParty) {
        this.allowParty = allowParty;
    }

    public List<String> getAllowUser() {
        return allowUser;
    }

    public void setAllowUser(List<String> allowUser) {
        this.allowUser = allowUser;
    }

    public List<Long> getAllowTag() {
        return allowTag;
    }

    public void setAllowTag(List<Long> allowTag) {
        this.allowTag = allowTag;
    }

    public List<Long> getExtraParty() {
        return extraParty;
    }

    public void setExtraParty(List<Long> extraParty) {
        this.extraParty = extraParty;
    }

    public List<String> getExtraUser() {
        return extraUser;
    }

    public void setExtraUser(List<String> extraUser) {
        this.extraUser = extraUser;
    }

    public List<Long> getExtraTag() {
        return extraTag;
    }

    public void setExtraTag(List<Long> extraTag) {
        this.extraTag = extraTag;
    }

    @Override
    public String toString() {
        return "CorpAuthPrivilegeVO{" +
                "level=" + level +
                ", allowParty=" + allowParty +
                ", allowUser=" + allowUser +
                ", allowTag=" + allowTag +
                ", extraParty=" + extraParty +
                ", extraUser=" + extraUser +
                ", extraTag=" + extraTag +
                '}';
    }
}
