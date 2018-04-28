package com.rishiqing.qywx.service.model.corp;

import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2018-04-28 10:30
 */
public class CorpTagDetailVO {
    private String corpId;
    private Long tagId;
    private String tagName;
    private List<String> userList;
    private List<Long> partyList;

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public List<String> getUserList() {
        return userList;
    }

    public void setUserList(List<String> userList) {
        this.userList = userList;
    }

    public List<Long> getPartyList() {
        return partyList;
    }

    public void setPartyList(List<Long> partyList) {
        this.partyList = partyList;
    }

    @Override
    public String toString() {
        return "CorpTagDetailVO{" +
                "corpId='" + corpId + '\'' +
                ", tagId=" + tagId +
                ", tagName='" + tagName + '\'' +
                ", userList=" + userList +
                ", partyList=" + partyList +
                '}';
    }
}
