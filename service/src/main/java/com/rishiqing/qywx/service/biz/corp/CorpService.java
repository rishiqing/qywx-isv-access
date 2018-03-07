package com.rishiqing.qywx.service.biz.corp;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.rishiqing.common.exception.ActiveCorpException;
import com.rishiqing.common.exception.HttpException;
import com.rishiqing.qywx.service.model.corp.CorpJsapiTicketVO;
import com.rishiqing.qywx.service.model.corp.CorpSuiteVO;
import com.rishiqing.qywx.service.model.corp.CorpVO;
import com.rishiqing.qywx.service.model.isv.SuiteTokenVO;

public interface CorpService {
    /**
     * 在企业初次授权时，根据auth_code获取永久授权码permanent_code，同时获取企业信息
     * @return
     */
    void activeCorp(String authCode) throws ActiveCorpException;

    /**
     * 根据企业的永久授权码获取企业信息
     * corpSuite中需要填入permanentCode
     * @return
     */
    CorpVO fetchAndSaveCorpInfo(SuiteTokenVO suiteTokenVO, CorpSuiteVO corpSuite);

    /**
     * 在用户企业授权变更时，调用此方法获取最新的企业授权并进行比对变更
     * @param suiteToken
     * @param corpSuite
     * @return
     * @throws UnirestException
     * @throws HttpException
     */
    CorpVO fetchAndChangeCorpInfo(SuiteTokenVO suiteToken, CorpSuiteVO corpSuite);

    /**
     * 获取并保存jsapi ticket
     * @param corpSuiteVO
     * @return
     */
    CorpJsapiTicketVO fetchAndSaveCorpJsapiTicket(CorpSuiteVO corpSuiteVO);
}
