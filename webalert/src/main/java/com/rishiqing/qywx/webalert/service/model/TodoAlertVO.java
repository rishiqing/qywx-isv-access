package com.rishiqing.qywx.webalert.service.model;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2018-04-10 15:52
 */
public class TodoAlertVO {
    private String todoId;
    private List<Long> millsList;
    private List<String> ruleList;
    private String userIdListString;
    private String msgType;
    private JSONObject msgContent;

    public String getTodoId() {
        return todoId;
    }

    public void setTodoId(String todoId) {
        this.todoId = todoId;
    }

    public List<Long> getMillsList() {
        return millsList;
    }

    public void setMillsList(List<Long> millsList) {
        this.millsList = millsList;
    }

    public List<String> getRuleList() {
        return ruleList;
    }

    public void setRuleList(List<String> ruleList) {
        this.ruleList = ruleList;
    }

    public String getUserIdListString() {
        return userIdListString;
    }

    public void setUserIdListString(String userIdListString) {
        this.userIdListString = userIdListString;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public JSONObject getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(JSONObject msgContent) {
        this.msgContent = msgContent;
    }

    @Override
    public String toString() {
        return "TodoAlertVO{" +
                "todoId='" + todoId + '\'' +
                ", millsList=" + millsList +
                ", ruleList=" + ruleList +
                ", userIdListString='" + userIdListString + '\'' +
                ", msgType='" + msgType + '\'' +
                ", msgContent=" + msgContent +
                '}';
    }
}
