package com.rishiqing.qywx.web.service.impl;

import com.rishiqing.qywx.service.biz.isv.SuiteManageService;
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
    private static final Logger logger = LoggerFactory.getLogger("ISV_CALLBACK_LOGGER");

    @Autowired
    private Map isvGlobal;
    @Autowired
    private SuiteManageService suiteManageService;

    private SuiteVO suite;

    @PostConstruct
    private void init(){
        //  读取套件基本信息
        String suiteKey = (String)isvGlobal.get("suiteKey");
        this.suite = suiteManageService.getSuiteInfoByKey(suiteKey);
    }

    @Override
    public String verifyUrl(String signature, String timestamp, String nonce, String echoString, String content) throws CallbackException {

        String token = this.suite.getToken();
        String suiteKey = this.suite.getSuiteKey();
        String encodingAesKey = this.suite.getEncodingAesKey();

        String resultStr = "success"; //需要返回的明文
        try {
            WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(token, encodingAesKey, suiteKey);

            if(echoString != null){
                //  走isv验证url流程
                resultStr = wxcpt.verifyURL(signature, timestamp, nonce, echoString);
            }else{
                //TODO  走isv接受消息的流程

            }

        } catch (AesException e) {
            throw new CallbackException("verify url failed", e);
        }
        return resultStr;
    }

    @Override
    public Map decryptMessage(String orgMessage) throws CallbackException {
        String token = this.suite.getToken();
        String suiteKey = this.suite.getSuiteKey();
        String encodingAesKey = this.suite.getEncodingAesKey();

        Map map;
        try {
            WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(token, encodingAesKey, suiteKey);
            String str = wxcpt.decryptMsg(signature, timestamp, nonce, body);
            map = XmlUtil.simpleXmlString2Map(str);

        } catch (AesException | ParserConfigurationException | IOException | SAXException e) {
            throw new CallbackException("decrypt message failed", e);
        }
        return map;
    }
}
