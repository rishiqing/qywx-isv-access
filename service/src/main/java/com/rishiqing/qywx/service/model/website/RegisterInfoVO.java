package com.rishiqing.qywx.service.model.website;

/**
 * @author Wallace Mao
 * “注册定制化”功能中，用户注册相关的信息
 * Date: 2018-05-10 16:34
 */
public class RegisterInfoVO {
    private String templateId;
    private String corpName;
    private String adminName;
    private String adminMobile;
    private String state;

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminMobile() {
        return adminMobile;
    }

    public void setAdminMobile(String adminMobile) {
        this.adminMobile = adminMobile;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "RegisterInfoVO{" +
                "templateId='" + templateId + '\'' +
                ", corpName='" + corpName + '\'' +
                ", adminName='" + adminName + '\'' +
                ", adminMobile='" + adminMobile + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
