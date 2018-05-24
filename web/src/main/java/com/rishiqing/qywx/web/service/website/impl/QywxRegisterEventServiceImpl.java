package com.rishiqing.qywx.web.service.website.impl;

import com.rishiqing.common.exception.ActiveCorpException;
import com.rishiqing.common.exception.ReauthCorpException;
import com.rishiqing.qywx.service.common.isv.GlobalSuite;
import com.rishiqing.qywx.service.common.isv.IsvManageService;
import com.rishiqing.qywx.service.constant.CallbackInfoType;
import com.rishiqing.qywx.service.exception.CallbackException;
import com.rishiqing.qywx.service.model.isv.IsvVO;
import com.rishiqing.qywx.web.service.website.QywxRegisterEventService;
import com.rishiqing.qywx.web.util.codec.AesException;
import com.rishiqing.qywx.web.util.codec.WXBizMsgCrypt;
import com.rishiqing.qywx.web.util.common.XmlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Map;

/**
 * @author Wallace Mao
 * Date: 2018-05-12 15:52
 */
public class QywxRegisterEventServiceImpl implements QywxRegisterEventService {
    public static final Logger logger = LoggerFactory.getLogger("CONSOLE_LOGGER");

    @Autowired
    private GlobalSuite suite;
    @Autowired
    private IsvManageService isvManageService;

    @Override
    public String verifyUrl(String signature, String timestamp, String nonce, String echoString) {
        String corpId = suite.getCorpId();
        IsvVO isv = isvManageService.getIsv(corpId);
        //  这里需要使用isv的token和key，而不能使用suite的token和key
        String token = isv.getToken();
        String encodingAesKey = isv.getEncodingAesKey();

        try {
            WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(token, encodingAesKey, corpId);
            return wxcpt.verifyURL(signature, timestamp, nonce, echoString);
        } catch (AesException e) {
            throw new CallbackException("verify url failed", e);
        }
    }

    @Override
    public String receiveMessage(String signature, String timestamp, String nonce, String body) {
        String corpId = suite.getCorpId();

        //  如果body中的ServiceCorpId不是服务商的corpId，那么就不做处理
        Boolean canHandle;
        try {
            canHandle = XmlUtil.checkIsvCorpId(corpId, body);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new CallbackException("fail to check isv corpId, request body is " + body, e);
        }
        if(!canHandle){
            return "success";
        }

        //  如果body中的ServiceCorpId确实是发送给isv的，那么就进行处理
        try {
            IsvVO isv = isvManageService.getIsv(corpId);
            String token = isv.getToken();
            String encodingAesKey = isv.getEncodingAesKey();

            WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(token, encodingAesKey, corpId);
            String str = wxcpt.decryptMsg(signature, timestamp, nonce, body);
            logger.info("----callback message----{}", str);
            Map map = XmlUtil.xmlString2Map(str);
            String infoType = (String)map.get("InfoType");
            CallbackInfoType type = CallbackInfoType.getCallbackInfoType(infoType);

            //  如果type没在枚举列表中，那么报出错误
            if(null == type){
                throw new CallbackException("type not recognized: " + infoType);
            }

            switch (type) {
                case REGISTER_CORP:
                    handleRegisterCorp(map);
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

    private void handleRegisterCorp(Map map){
        //  这里可能需要保存用户的注册信息
    }
}
