package com.rishiqing.qywx.model.corp;

import java.util.Date;

/**
 * 企业DO
 */
public class CorpDO {
    private Long id;
    private Date dateCreated;
    private Date lastUpdated;

    private String corpName;

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

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    @Override
    public String toString() {
        return "CorpDO{" +
                "id=" + id +
                ", dateCreated=" + dateCreated +
                ", lastUpdated=" + lastUpdated +
                ", corpName='" + corpName + '\'' +
                '}';
    }
}
