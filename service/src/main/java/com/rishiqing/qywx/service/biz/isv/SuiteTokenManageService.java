package com.rishiqing.qywx.service.biz.isv;

import com.rishiqing.qywx.service.model.isv.SuiteTokenVO;

public interface SuiteTokenManageService {
    /**
     * 根据suiteKey获取suiteToken
     * @param suiteKey
     * @return
     */
    SuiteTokenVO getSuiteToken(String suiteKey);

    /**
     * 保存suiteTicket
     * @param suiteTokenVO
     */
    void saveSuiteToken(SuiteTokenVO suiteTokenVO);
}
