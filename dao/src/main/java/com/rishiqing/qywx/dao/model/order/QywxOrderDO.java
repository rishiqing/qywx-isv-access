package com.rishiqing.qywx.dao.model.order;

import java.util.Date;

/**
 * 订单状态表，此表用来记录订单的状态
 * @link https://work.weixin.qq.com/api/doc#15219/%E6%9C%8D%E5%8A%A1%E5%95%86%E6%8B%89%E5%8F%96%E4%BB%98%E8%B4%B9%E7%8A%B6%E6%80%81
 * @author Wallace Mao
 * Date: 2018-10-18 16:38
 */
public class QywxOrderDO {
    private Long id;
    private Date dateCreated;
    private Date lastUpdated;

    private String orderid;
    private String orderStatus;
    private String orderType;
    private String paidCorpid;
    private String operatorId;
    private String suiteid;
    private String appid;
    private String editionId;
    private String editionName;
    //  以分为单位的价格
    private Long price;
    private Long userCount;
    //  以天为单位
    private Long orderPeriod;
    //  以秒为单位
    private Long orderTime;
    //  以秒为单位
    private Long paidTime;

    private String chargeStatus;
    private Date chargeTime;

    private String changedOrderid;

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

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getPaidCorpid() {
        return paidCorpid;
    }

    public void setPaidCorpid(String paidCorpid) {
        this.paidCorpid = paidCorpid;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getSuiteid() {
        return suiteid;
    }

    public void setSuiteid(String suiteid) {
        this.suiteid = suiteid;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getEditionId() {
        return editionId;
    }

    public void setEditionId(String editionId) {
        this.editionId = editionId;
    }

    public String getEditionName() {
        return editionName;
    }

    public void setEditionName(String editionName) {
        this.editionName = editionName;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getUserCount() {
        return userCount;
    }

    public void setUserCount(Long userCount) {
        this.userCount = userCount;
    }

    public Long getOrderPeriod() {
        return orderPeriod;
    }

    public void setOrderPeriod(Long orderPeriod) {
        this.orderPeriod = orderPeriod;
    }

    public Long getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Long orderTime) {
        this.orderTime = orderTime;
    }

    public Long getPaidTime() {
        return paidTime;
    }

    public void setPaidTime(Long paidTime) {
        this.paidTime = paidTime;
    }

    public String getChargeStatus() {
        return chargeStatus;
    }

    public void setChargeStatus(String chargeStatus) {
        this.chargeStatus = chargeStatus;
    }

    public Date getChargeTime() {
        return chargeTime;
    }

    public void setChargeTime(Date chargeTime) {
        this.chargeTime = chargeTime;
    }

    public String getChangedOrderid() {
        return changedOrderid;
    }

    public void setChangedOrderid(String changedOrderid) {
        this.changedOrderid = changedOrderid;
    }

    @Override
    public String toString() {
        return "QywxOrderDO{" +
                "id=" + id +
                ", dateCreated=" + dateCreated +
                ", lastUpdated=" + lastUpdated +
                ", orderid='" + orderid + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", orderType='" + orderType + '\'' +
                ", paidCorpid='" + paidCorpid + '\'' +
                ", operatorId='" + operatorId + '\'' +
                ", suiteid='" + suiteid + '\'' +
                ", appid='" + appid + '\'' +
                ", editionId='" + editionId + '\'' +
                ", editionName='" + editionName + '\'' +
                ", price=" + price +
                ", userCount=" + userCount +
                ", orderPeriod=" + orderPeriod +
                ", orderTime=" + orderTime +
                ", paidTime=" + paidTime +
                ", chargeStatus='" + chargeStatus + '\'' +
                ", chargeTime=" + chargeTime +
                ", changedOrderid='" + changedOrderid + '\'' +
                '}';
    }
}
