package com.rishiqing.qywx.service.event.message;

/**
 * @author Wallace Mao
 * Date: 2019-02-14 16:38
 */
public class CorpTrialMessage {
    private String corpId;

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    @Override
    public String toString() {
        return "CorpTrialMessage{" +
                "corpId='" + corpId + '\'' +
                '}';
    }
}
