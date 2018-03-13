package com.rishiqing.qywx.service.common.isv;

import com.rishiqing.qywx.service.model.isv.SuitePreAuthCodeVO;
import com.rishiqing.qywx.service.model.isv.SuiteTokenVO;

public interface SuitePreAuthCodeManageService {
    /**
     * 根据suiteKey获取suitePreAuthCode
     * @param suiteKey
     * @return
     */
    SuitePreAuthCodeVO getSuitePreAuthCode(String suiteKey);

    /**
     * 保存suitePreAuthCodeVO到本地
     * @param suitePreAuthCodeVO
     */
    void saveSuitePreAuthCode(SuitePreAuthCodeVO suitePreAuthCodeVO);
}
