package com.rishiqing.common.model;

/**
 * @author Wallace Mao
 * Date: 2018-02-08 15:57
 * 日事清的department对象，各属性除了特殊标注，均与日事清中保持一致
 */
public class RsqDepartmentVO {
    private Long id;
    private String name;
    private String parentId;
    private String teamId;
    private Long orderNum;
    private String outerId;

    private String corpId;  //本地的corpId
    private String deptId;  //本地的dept

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public Long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Long orderNum) {
        this.orderNum = orderNum;
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

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    @Override
    public String toString() {
        return "RsqDepartmentVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parentId='" + parentId + '\'' +
                ", teamId='" + teamId + '\'' +
                ", orderNum=" + orderNum +
                ", outerId='" + outerId + '\'' +
                ", corpId='" + corpId + '\'' +
                ", deptId='" + deptId + '\'' +
                '}';
    }
}
