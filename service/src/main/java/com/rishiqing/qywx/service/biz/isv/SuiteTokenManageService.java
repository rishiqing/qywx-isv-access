package com.rishiqing.qywx.service.biz.isv;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.rishiqing.qywx.service.exception.HttpException;
import com.rishiqing.qywx.service.model.isv.SuiteTokenVO;

public interface SuiteTokenManageService {
    /**
     * 根据suiteKey获取suiteToken
     * @param suiteKey
     * @return
     */
    SuiteTokenVO getSuiteToken(String suiteKey);

    /**
     * 根据suiteKey，获取从微信服务器获取到对应suite的token，并保存到本地
     * @param suiteKey
     */
    void fetchAndSaveSuiteToken(String suiteKey) throws HttpException, UnirestException;
}
