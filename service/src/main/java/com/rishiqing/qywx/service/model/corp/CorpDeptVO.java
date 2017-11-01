package com.rishiqing.qywx.service.model.corp;

import java.util.Date;

public class CorpDeptVO {
    private Long id;

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
        return "CorpDeptVO{" +
                "id=" + id +
                ", corpId='" + corpId + '\'' +
                ", deptId=" + deptId +
                ", name='" + name + '\'' +
                ", order=" + order +
                ", parentId=" + parentId +
                ", rsqId='" + rsqId + '\'' +
                '}';
    }
}
