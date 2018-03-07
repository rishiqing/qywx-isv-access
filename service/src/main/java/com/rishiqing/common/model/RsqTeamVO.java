package com.rishiqing.common.model;

/**
 * @author Wallace Mao
 * Date: 2018-02-08 15:57
 * 日事清的team对象，各属性除了特殊标注，均与日事清中保持一致
 */
public class RsqTeamVO {
    private Long id;
    private String name;
    private String outerId;
    private String note;
    private Boolean isExists;
    private String fromApp;

    private String corpId;  //  本地的corpId

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

    public String getOuterId() {
        return outerId;
    }

    public void setOuterId(String outerId) {
        this.outerId = outerId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Boolean getExists() {
        return isExists;
    }

    public void setExists(Boolean exists) {
        isExists = exists;
    }

    public String getFromApp() {
        return fromApp;
    }

    public void setFromApp(String fromApp) {
        this.fromApp = fromApp;
    }

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    @Override
    public String toString() {
        return "RsqTeamVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", outerId='" + outerId + '\'' +
                ", note='" + note + '\'' +
                ", isExists=" + isExists +
                ", fromApp='" + fromApp + '\'' +
                ", corpId='" + corpId + '\'' +
                '}';
    }
}
