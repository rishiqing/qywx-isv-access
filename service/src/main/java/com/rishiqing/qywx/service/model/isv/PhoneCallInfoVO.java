package com.rishiqing.qywx.service.model.isv;

/**
 * @author Wallace Mao
 * Date: 2018-06-19 20:14
 */
public class PhoneCallInfoVO {
    private String callerUserId;
    private String calleeUserId;
    private String calleeCorpId;

    public String getCallerUserId() {
        return callerUserId;
    }

    public void setCallerUserId(String callerUserId) {
        this.callerUserId = callerUserId;
    }

    public String getCalleeUserId() {
        return calleeUserId;
    }

    public void setCalleeUserId(String calleeUserId) {
        this.calleeUserId = calleeUserId;
    }

    public String getCalleeCorpId() {
        return calleeCorpId;
    }

    public void setCalleeCorpId(String calleeCorpId) {
        this.calleeCorpId = calleeCorpId;
    }

    @Override
    public String toString() {
        return "PhoneCallInfoVO{" +
                "callerUserId='" + callerUserId + '\'' +
                ", calleeUserId='" + calleeUserId + '\'' +
                ", calleeCorpId='" + calleeCorpId + '\'' +
                '}';
    }
}
