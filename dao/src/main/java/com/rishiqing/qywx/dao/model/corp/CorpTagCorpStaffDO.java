package com.rishiqing.qywx.dao.model.corp;

import java.util.Date;

/**
 * tag与staff和dept的关联关系，tag与staff有关联时，dept为null；tag与dept有关联时，staff为null
 * @author Wallace Mao
 * Date: 2018-04-27 16:53
 */
public class CorpTagCorpStaffDO {
    private Long id;
    private Date dateCreated;
    private Date lastUpdated;

    private String corpId;
    private Long tagId;
    private String userId;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "CorpTagCorpStaffDO{" +
                "id=" + id +
                ", dateCreated=" + dateCreated +
                ", lastUpdated=" + lastUpdated +
                ", corpId='" + corpId + '\'' +
                ", tagId=" + tagId +
                ", userId='" + userId + '\'' +
                '}';
    }
}
