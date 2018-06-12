package com.rishiqing.qywx.dao.model.message;

import java.util.Date;

/**
 * @author Wallace Mao
 * Date: 2018-05-30 18:07
 */
public class IsvMessage {
    private Long id;
    private Date dateCreated;
    private Date lastUpdated;

    private String messageKey;
    private String messageType;
    private String jsonContent;

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

    public String getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getJsonContent() {
        return jsonContent;
    }

    public void setJsonContent(String jsonContent) {
        this.jsonContent = jsonContent;
    }

    @Override
    public String toString() {
        return "IsvMessage{" +
                "id=" + id +
                ", dateCreated=" + dateCreated +
                ", lastUpdated=" + lastUpdated +
                ", messageKey='" + messageKey + '\'' +
                ", messageType='" + messageType + '\'' +
                ", jsonContent='" + jsonContent + '\'' +
                '}';
    }
}
