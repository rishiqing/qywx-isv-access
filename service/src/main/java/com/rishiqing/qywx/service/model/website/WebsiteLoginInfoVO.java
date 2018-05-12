package com.rishiqing.qywx.service.model.website;

import com.rishiqing.qywx.service.model.corp.CorpStaffVO;

/**
 * @author Wallace Mao
 * Date: 2018-05-10 19:48
 */
public class WebsiteLoginInfoVO {
    private Long userType;
    private CorpStaffVO corpStaffVO;

    public Long getUserType() {
        return userType;
    }

    public void setUserType(Long userType) {
        this.userType = userType;
    }

    public CorpStaffVO getCorpStaffVO() {
        return corpStaffVO;
    }

    public void setCorpStaffVO(CorpStaffVO corpStaffVO) {
        this.corpStaffVO = corpStaffVO;
    }

    @Override
    public String toString() {
        return "WebsiteLoginInfoVO{" +
                "userType=" + userType +
                ", corpStaffVO=" + corpStaffVO +
                '}';
    }
}
