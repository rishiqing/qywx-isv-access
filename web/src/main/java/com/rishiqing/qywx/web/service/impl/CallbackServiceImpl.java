package com.rishiqing.qywx.web.service.impl;

import com.rishiqing.qywx.service.biz.isv.SuiteManageService;
import com.rishiqing.qywx.service.biz.isv.SuiteTicketManageService;
import com.rishiqing.qywx.service.biz.isv.SuiteTokenManageService;
import com.rishiqing.qywx.service.model.isv.SuiteTicketVO;
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

import javax.annotation.PostConstruct;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Map;

public class CallbackServiceImpl implements CallbackService {

    @Autowired
    private Map isvGlobal;
    @Autowired
    private SuiteManageService suiteManageService;
    @Autowired
    private SuiteTicketManageService suiteTicketManageService;
    @Autowired
    private SuiteTokenManageService suiteTokenManageService;

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
            Map map = XmlUtil.simpleXmlString2Map(str);
            String infoType = (String)map.get("InfoType");

            switch (infoType) {
                case "suite_ticket":
                    handleSuiteTicket(map);
                    break;
                default:
                    //  对于不识别的infoType，直接抛出异常
                    throw new CallbackException("info type not handled: " + infoType);
            }

        } catch (AesException | ParserConfigurationException | IOException | SAXException e) {
            throw new CallbackException("decrypt message failed", e);
        }
        return "success";
    }

    private void handleSuiteTicket(Map params){
        String ticket = (String)params.get("SuiteTicket");
        assert ticket != null;
        SuiteTicketVO suiteTicketVO = new SuiteTicketVO();
        suiteTicketVO.setSuiteKey(this.suite.getSuiteKey());
        suiteTicketVO.setTicket(ticket);
        suiteTicketManageService.saveSuiteTicket(suiteTicketVO);
    }

    private void handleCreateAuth(Map params){}

    private void handleChangeAuth(Map params){}

    private void handleCancelAuth(Map params){}

    private void handleChangeContact(Map params){}
}
