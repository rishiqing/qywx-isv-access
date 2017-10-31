package com.rishiqing.qywx.web.service.impl;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.rishiqing.qywx.service.biz.corp.CorpManageService;
import com.rishiqing.qywx.service.biz.isv.SuiteManageService;
import com.rishiqing.qywx.service.biz.isv.SuiteTicketManageService;
import com.rishiqing.qywx.service.biz.isv.SuiteTokenManageService;
import com.rishiqing.qywx.service.exception.HttpException;
import com.rishiqing.qywx.service.exception.SuiteAccessTokenExpiredException;
import com.rishiqing.qywx.service.model.isv.SuiteTicketVO;
import com.rishiqing.qywx.service.model.isv.SuiteTokenVO;
import com.rishiqing.qywx.service.model.isv.SuiteVO;
import com.rishiqing.qywx.web.exception.CallbackException;
import com.rishiqing.qywx.web.service.CallbackService;
import com.rishiqing.qywx.web.util.codec.AesException;
import com.rishiqing.qywx.web.util.codec.WXBizMsgCrypt;
import com.rishiqing.qywx.web.util.common.XmlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.SAXException;
import sun.rmi.runtime.Log;

import javax.annotation.PostConstruct;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Map;

public class CallbackServiceImpl implements CallbackService {
    private static final Logger logger = LoggerFactory.getLogger("ISV_CALLBACK_LOGGER");

    @Autowired
    private Map isvGlobal;
    @Autowired
    private SuiteManageService suiteManageService;
    @Autowired
    private SuiteTicketManageService suiteTicketManageService;
    @Autowired
    private SuiteTokenManageService suiteTokenManageService;
    @Autowired
    private CorpManageService corpManageService;

    private SuiteVO suite;

    @PostConstruct
    private void init(){
        //  读取套件基本信息
        String suiteKey = (String)isvGlobal.get("suiteKey");
        this.suite = suiteManageService.getSuiteInfoByKey(suiteKey);
    }

    @Override
    public String verifyUrl(String signature, String timestamp, String nonce, String echoString) throws CallbackException {

        String token = this.suite.getToken();
        String suiteKey = this.suite.getSuiteKey();
        String encodingAesKey = this.suite.getEncodingAesKey();

        try {
            WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(token, encodingAesKey, suiteKey);
            return wxcpt.verifyURL(signature, timestamp, nonce, echoString);
        } catch (AesException e) {
            throw new CallbackException("verify url failed", e);
        }
    }

    @Override
    public String receiveMessage(String signature, String timestamp, String nonce, String body) throws CallbackException {
        String token = this.suite.getToken();
        String suiteKey = this.suite.getSuiteKey();
        String encodingAesKey = this.suite.getEncodingAesKey();

        try {
            WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(token, encodingAesKey, suiteKey);
            String str = wxcpt.decryptMsg(signature, timestamp, nonce, body);
            logger.info("----callback message----" + str);
            Map map = XmlUtil.simpleXmlString2Map(str);
            String infoType = (String)map.get("InfoType");

            switch (infoType) {
                case "suite_ticket":
                    handleSuiteTicket(map);
                    break;
                case "create_auth":
                    handleCreateAuth(map);
                    break;
                case "cancel_auth":
                    handleCancelAuth(map);
                default:
                    //  对于不识别的infoType，直接抛出异常
                    throw new CallbackException("info type not handled: " + infoType);
            }

        } catch (AesException | ParserConfigurationException | IOException | SAXException e) {
            throw new CallbackException("decrypt message failed", e);
        } catch (UnirestException | HttpException e) {
            throw new CallbackException("http request failed", e);
        } catch (SuiteAccessTokenExpiredException e) {
            //  如果是suiteAccessTokenExpiredException，那么先重新获取suiteAccessToken，然后在执行本方法
//            suiteTokenManageService.fetchAndSaveSuiteToken(suiteKey);
            logger.info("suite access token expired");
            try {
                suiteTokenManageService.fetchAndSaveSuiteToken(suiteKey);
            } catch (HttpException | UnirestException e1) {
                //  需要加入重试机制
                throw new CallbackException("http request failed in fetch expired suite access token", e1);
            }
            this.receiveMessage(signature, timestamp, nonce, body);
        }
        return "success";
    }

    /**
     * 推送ticket的回调
     * @param params
     */
    private void handleSuiteTicket(Map params){
        String ticket = (String)params.get("SuiteTicket");
        assert ticket != null;
        SuiteTicketVO suiteTicketVO = new SuiteTicketVO();
        suiteTicketVO.setSuiteKey(this.suite.getSuiteKey());
        suiteTicketVO.setTicket(ticket);
        suiteTicketManageService.saveSuiteTicket(suiteTicketVO);
    }

    /**
     * 授权成功的回调，获取返回值中包括临时授权码
     * @param params
     */
    private void handleCreateAuth(Map params) throws SuiteAccessTokenExpiredException, UnirestException, HttpException {
        String authCode = (String)params.get("AuthCode");
        assert authCode != null;
        System.out.println("-----handleCrateAuth----" + authCode);
        SuiteTokenVO suiteTokenVO = suiteTokenManageService.getSuiteToken(this.suite.getSuiteKey());
        corpManageService.activeCorp(suiteTokenVO, authCode);
    }

    private void handleChangeAuth(Map params){}

    /**
     * 取消授权，对corp做标记
     * @param params
     */
    private void handleCancelAuth(Map params){}

    private void handleChangeContact(Map params){}
}
