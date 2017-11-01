package com.rishiqing.qywx.dao.model.corp;

import java.util.Date;

public class CorpDeptDO {
    private Long id;
    private Date dateCreated;
    private Date lastUpdated;

    private String corpId;
    private Long deptId;
    private String name;
    private Long order;
    private Long parentId;

    private String rsqId;

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

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getRsqId() {
        return rsqId;
    }

    public void setRsqId(String rsqId) {
        this.rsqId = rsqId;
    }

    @Override
    public String toString() {
        return "CorpDeptDO{" +
                "id=" + id +
                ", dateCreated=" + dateCreated +
                ", lastUpdated=" + lastUpdated +
                ", corpId='" + corpId + '\'' +
                ", deptId=" + deptId +
                ", name='" + name + '\'' +
                ", order=" + order +
                ", parentId=" + parentId +
                ", rsqId='" + rsqId + '\'' +
                '}';
    }
}
