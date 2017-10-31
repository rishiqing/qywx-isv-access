package com.rishiqing.qywx.service.model.isv;

import java.util.Date;

public class SuiteTicketVO {
    private Long id;

    private String suiteKey;
    private String ticket;
    private Date ticketUpdateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSuiteKey() {
        return suiteKey;
    }

    public void setSuiteKey(String suiteKey) {
        this.suiteKey = suiteKey;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Date getTicketUpdateTime() {
        return ticketUpdateTime;
    }

    public void setTicketUpdateTime(Date ticketUpdateTime) {
        this.ticketUpdateTime = ticketUpdateTime;
    }

    @Override
    public String toString() {
        return "SuiteTicketVO{" +
                "id=" + id +
                ", suiteKey='" + suiteKey + '\'' +
                ", ticket='" + ticket + '\'' +
                ", ticketUpdateTime=" + ticketUpdateTime +
                '}';
    }
}
