package com.rishiqing.qywx.web.service.impl;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.rishiqing.common.exception.ActiveCorpException;
import com.rishiqing.common.exception.ReauthCorpException;
import com.rishiqing.qywx.service.biz.corp.CorpService;
import com.rishiqing.qywx.service.biz.corp.DeptService;
import com.rishiqing.qywx.service.biz.corp.StaffService;
import com.rishiqing.qywx.service.callback.FetchCallbackHandler;
import com.rishiqing.qywx.service.common.corp.CorpManageService;
import com.rishiqing.qywx.service.common.corp.CorpSuiteManageService;
import com.rishiqing.qywx.service.common.isv.GlobalSuite;
import com.rishiqing.qywx.service.common.isv.SuiteTicketManageService;
import com.rishiqing.qywx.service.common.isv.SuiteTokenManageService;
import com.rishiqing.common.exception.HttpException;
import com.rishiqing.qywx.service.constant.CallbackChangeType;
import com.rishiqing.qywx.service.constant.CallbackInfoType;
import com.rishiqing.qywx.service.event.service.QueueService;
import com.rishiqing.qywx.service.model.corp.CorpSuiteVO;
import com.rishiqing.qywx.service.model.corp.CorpVO;
import com.rishiqing.qywx.service.model.isv.SuiteTicketVO;
import com.rishiqing.qywx.service.model.isv.SuiteTokenVO;
import com.rishiqing.qywx.service.exception.CallbackException;
import com.rishiqing.qywx.web.service.CallbackService;
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

public class CallbackServiceImpl implements CallbackService {
    private static final Logger logger = LoggerFactory.getLogger("WEB_CALLBACK_LOGGER");

    @Autowired
    private GlobalSuite suite;
    @Autowired
    private SuiteTicketManageService suiteTicketManageService;
    @Autowired
    private SuiteTokenManageService suiteTokenManageService;
    @Autowired
    private CorpManageService corpManageService;
    @Autowired
    private CorpService corpService;
    @Autowired
    private CorpSuiteManageService corpSuiteManageService;
    @Autowired
    private DeptService deptService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private QueueService queueService;
    @Autowired
    private FetchCallbackHandler logFailFetchCallbackHandler;

    @Override
    public String verifyUrl(String signature, String timestamp, String nonce, String echoString) {

        String token = suite.getToken();
        String suiteKey = suite.getSuiteKey();
        String encodingAesKey = suite.getEncodingAesKey();
        String corpId = suite.getCorpId();

        try {
            WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(token, encodingAesKey, corpId);
            return wxcpt.verifyURL(signature, timestamp, nonce, echoString);
        } catch (AesException e) {
            throw new CallbackException("verify url failed", e);
        }
    }

    @Override
    public String receiveMessage(String signature, String timestamp, String nonce, String body) throws ActiveCorpException, ReauthCorpException {
        String token = suite.getToken();
        String suiteKey = suite.getSuiteKey();
        String encodingAesKey = suite.getEncodingAesKey();

        //  如果body中的ToUserName不是suiteKey，那么就不做处理
        Boolean canHandle;
        try {
            canHandle = XmlUtil.checkUserName(suiteKey, body);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new CallbackException("fail to check user name, request body is " + body, e);
        }
        if(!canHandle){
            return "success";
        }

        //  如果body中的ToUserName确实是发送给isv的，那么就进行处理
        try {
            WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(token, encodingAesKey, suiteKey);
            String str = wxcpt.decryptMsg(signature, timestamp, nonce, body);
            logger.info("----callback message----{}", str);
            Map map = XmlUtil.simpleXmlString2Map(str);
            String infoType = (String)map.get("InfoType");
            CallbackInfoType type = CallbackInfoType.getCallbackInfoType(infoType);

            //  如果type没在枚举列表中，那么报出错误
            if(null == type){
                throw new CallbackException("type not recognized: " + infoType);
            }

            switch (type) {
                case SUITE_TICKET:
                    handleSuiteTicket(map);
                    break;
                case CREATE_AUTH:
                    handleCreateAuth(map);
                    break;
                case CHANGE_AUTH:
                    handleChangeAuth(map);
                    break;
                case CANCEL_AUTH:
                    handleCancelAuth(map);
                    break;
                case CHANGE_CONTACT:
                    //  通讯录变更,包括部门变更/人员变更
                    handleChangeContact(map);
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

    /**
     * 推送ticket的回调
     * @param params
     */
    private void handleSuiteTicket(Map params){
        String ticket = (String)params.get("SuiteTicket");
        assert ticket != null;
        SuiteTicketVO suiteTicketVO = new SuiteTicketVO();
        suiteTicketVO.setSuiteKey(suite.getSuiteKey());
        suiteTicketVO.setTicket(ticket);
        suiteTicketManageService.saveSuiteTicket(suiteTicketVO);
    }

    /**
     * 授权成功的回调，获取返回值中包括临时授权码
     * @param params
     */
    private void handleCreateAuth(Map params) throws ActiveCorpException {
        String authCode = (String)params.get("AuthCode");
        assert authCode != null;
        //   activeCorp方法内需要马上返回，耗时操作异步执行
        corpService.activeCorp(authCode);
    }

    /**
     * 授权变更
     * @param params
     * @throws UnirestException
     * @throws HttpException
     */
    private void handleChangeAuth(Map params) throws ReauthCorpException {
        String corpId = (String)params.get("AuthCorpId");
        assert corpId != null;
        //   授权变更方法内需要马上返回，耗时操作异步执行
        corpService.reauthCorp(corpId);

//        String suiteKey = suite.getSuiteKey();
//        SuiteTokenVO suiteTokenVO = suiteTokenManageService.getSuiteToken(suiteKey);
//        CorpSuiteVO corpSuiteVO = corpSuiteManageService.getCorpSuite(suiteKey, corpId);
//        corpService.fetchAndChangeCorpInfo(suiteTokenVO, corpSuiteVO);
    }

    /**
     * 取消授权，对corp做标记
     * @param params
     */
    private void handleCancelAuth(Map params) {
        String corpId = (String)params.get("AuthCorpId");
        assert corpId != null;
        corpManageService.markRemoveCorp(corpId, true);
    }

    /**
     * 通讯录变更,包括了部门变更/成员变更等
     * @param map
     */
    private void handleChangeContact(Map map) {
        String changeType = (String)map.get("ChangeType");
        String corpId = (String)map.get("AuthCorpId");
        //  发送异步消息
        CorpVO corpVO = corpManageService.getCorpByCorpId(corpId);
        CallbackChangeType type = CallbackChangeType.getCallbackChangeType(changeType);

        //  如果type没在枚举列表中，那么报出错误
        if(null == type){
            throw new CallbackException("type not recognized: " + changeType);
        }

        switch (type) {
            case CREATE_PARTY:
                logFailFetchCallbackHandler.handleChangeContactCreateDept(map);
                break;
            case UPDATE_PARTY:
                logFailFetchCallbackHandler.handleChangeContactUpdateDept(map);
                break;
            case DELETE_PARTY:
                logFailFetchCallbackHandler.handleChangeContactDeleteDept(map);
                break;
            case CREATE_USER:
                logFailFetchCallbackHandler.handleChangeContactCreateUser(map);
                break;
            case UPDATE_USER:
                logFailFetchCallbackHandler.handleChangeContactUpdateUser(map);
                break;
            case DELETE_USER:
                logFailFetchCallbackHandler.handleChangeContactDeleteUser(map);
                break;
            case UPDATE_TAG:
                throw new CallbackException("UPDATE_TAG not supported now" + changeType);
            default:
                //  对于不识别的infoType，直接抛出异常
                throw new CallbackException("contact change, changeType not handled: " + changeType);
        }
        queueService.sendToPushCorpCallback(corpVO, type, map);
    }
}
