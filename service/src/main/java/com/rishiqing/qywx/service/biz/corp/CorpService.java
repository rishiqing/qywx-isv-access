package com.rishiqing.qywx.service.biz.corp;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.rishiqing.qywx.service.exception.HttpException;
import com.rishiqing.qywx.service.exception.SuiteAccessTokenExpiredException;
import com.rishiqing.qywx.service.model.corp.CorpSuiteVO;
import com.rishiqing.qywx.service.model.corp.CorpVO;
import com.rishiqing.qywx.service.model.isv.SuiteTokenVO;

public interface CorpService {
    /**
     * 在企业初次授权时，根据auth_code获取永久授权码permanent_code，同时获取企业信息
     * @return
     */
    CorpVO activeCorp(SuiteTokenVO suiteToken, String authCode) throws UnirestException, HttpException;

    /**
     * 根据企业的永久授权码获取企业信息
     * @return
     */
    CorpVO fetchAndSaveCorpInfo(SuiteTokenVO suiteTokenVO, CorpSuiteVO corpSuiteVO) throws UnirestException, HttpException;

}
