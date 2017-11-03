package com.rishiqing.qywx.service.common.isv;

import com.rishiqing.qywx.service.model.isv.SuiteTokenVO;

public interface SuiteTokenManageService {
    /**
     * 根据suiteKey获取suiteToken
     * @param suiteKey
     * @return
     */
    SuiteTokenVO getSuiteToken(String suiteKey);

    /**
     * 保存suiteTokenVO到本地
     * @param suiteTokenVO
     */
    void saveSuiteToken(SuiteTokenVO suiteTokenVO);
}
