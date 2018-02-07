package com.rishiqing.qywx.web.service.impl;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.rishiqing.qywx.service.biz.corp.CorpService;
import com.rishiqing.qywx.service.biz.corp.DeptService;
import com.rishiqing.qywx.service.biz.corp.StaffService;
import com.rishiqing.qywx.service.common.corp.CorpManageService;
import com.rishiqing.qywx.service.common.corp.CorpSuiteManageService;
import com.rishiqing.qywx.service.common.isv.SuiteManageService;
import com.rishiqing.qywx.service.common.isv.SuiteTicketManageService;
import com.rishiqing.qywx.service.common.isv.SuiteTokenManageService;
import com.rishiqing.qywx.service.exception.HttpException;
import com.rishiqing.qywx.service.exception.ObjectNotExistException;
import com.rishiqing.qywx.service.exception.SuiteAccessTokenExpiredException;
import com.rishiqing.qywx.service.model.corp.CorpDeptVO;
import com.rishiqing.qywx.service.model.corp.CorpStaffVO;
import com.rishiqing.qywx.service.model.corp.CorpSuiteVO;
import com.rishiqing.qywx.service.model.isv.SuiteTicketVO;
import com.rishiqing.qywx.service.model.isv.SuiteTokenVO;
import com.rishiqing.qywx.service.model.isv.SuiteVO;
import com.rishiqing.qywx.service.util.http.converter.Xml2BeanConverter;
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
    private static final Logger logger = LoggerFactory.getLogger("WEB_CALLBACK_LOGGER");

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
    @Autowired
    private CorpService corpService;
    @Autowired
    private CorpSuiteManageService corpSuiteManageService;
    @Autowired
    private DeptService deptService;
    @Autowired
    private StaffService staffService;

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
        String corpId = this.suite.getCorpId();

        try {
            WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(token, encodingAesKey, corpId);
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
                case "change_auth":
                    handleChangeAuth(map);
                    break;
                case "cancel_auth":
                    handleCancelAuth(map);
                    break;
                case "change_contact":
                    //  通讯录变更,包括部门变更/人员变更
                    handleChangeContact(map);
                    break;
                default:
                    //  对于不识别的infoType，直接抛出异常
                    throw new CallbackException("info type not handled: " + infoType);
            }

        } catch (AesException | ParserConfigurationException | IOException | SAXException e) {
            throw new CallbackException("decrypt message failed", e);
        } catch (UnirestException | HttpException e) {
            throw new CallbackException("http request failed", e);
        } catch (ObjectNotExistException e) {
            throw new CallbackException("database error", e);
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
    private void handleCreateAuth(Map params) throws UnirestException, HttpException {
        String authCode = (String)params.get("AuthCode");
        assert authCode != null;
        SuiteTokenVO suiteTokenVO = suiteTokenManageService.getSuiteToken(this.suite.getSuiteKey());
        corpService.activeCorp(suiteTokenVO, authCode);
    }

    /**
     * 授权变更
     * @param params
     * @throws UnirestException
     * @throws HttpException
     */
    private void handleChangeAuth(Map params) throws UnirestException, HttpException {
        String corpId = (String)params.get("AuthCorpId");
        assert corpId != null;
        String suiteKey = this.suite.getSuiteKey();
        SuiteTokenVO suiteTokenVO = suiteTokenManageService.getSuiteToken(suiteKey);
        CorpSuiteVO corpSuiteVO = corpSuiteManageService.getCorpSuite(suiteKey, corpId);
        corpService.fetchAndSaveCorpInfo(suiteTokenVO, corpSuiteVO);
    }

    /**
     * 取消授权，对corp做标记
     * @param params
     */
    private void handleCancelAuth(Map params){
        String corpId = (String)params.get("AuthCorpId");
        assert corpId != null;
        corpManageService.markRemoveCorp(corpId, true);
    }

    /**
     * 通讯录变更,包括了部门变更/成员变更等
     * @param map
     */
    private void handleChangeContact(Map map) throws CallbackException, ObjectNotExistException {
        String changeType = (String)map.get("ChangeType");
        switch (changeType) {
            case "create_party":
                handleChangeContactCreateDept(map);
                break;
            case "update_party":
                handleChangeContactUpdateDept(map);
                break;
            case "delete_party":
                handleChangeContactDeleteDept(map);
                break;
            case "create_user":
                handleChangeContactCreateUser(map);
                break;
            case "update_user":
                handleChangeContactUpdateUser(map);
                break;
            case "delete_user":
                handleChangeContactDeleteUser(map);
                break;
            default:
                //  对于不识别的infoType，直接抛出异常
                throw new CallbackException("contact change, changeType not handled: " + changeType);
        }
    }

    /**
     * 通讯录变更之创建部门
     * @param map
     */
    private void handleChangeContactCreateDept(Map map){
        CorpDeptVO deptVO = Xml2BeanConverter.generateCorpDept(map);
        deptService.createDept(deptVO);
    }

    /**
     * 通讯录变更之更新部门
     * @param map
     */
    private void handleChangeContactUpdateDept(Map map) throws ObjectNotExistException {
        CorpDeptVO deptVO = Xml2BeanConverter.generateCorpDept(map);
        deptService.updateDept(deptVO);
    }

    /**
     * 通讯录变更之删除部门
     * @param map
     */
    private void handleChangeContactDeleteDept(Map map) throws ObjectNotExistException {
        CorpDeptVO deptVO = Xml2BeanConverter.generateCorpDept(map);
        deptService.deleteDept(deptVO);
    }

    /**
     * 通讯录变更之添加成员
     * @param map
     */
    private void handleChangeContactCreateUser(Map map){
        CorpStaffVO staffVO = Xml2BeanConverter.generateCorpStaff(map);
        staffService.createStaff(staffVO);
    }

    /**
     * 通讯录变更之更新成员
     * @param map
     */
    private void handleChangeContactUpdateUser(Map map) throws ObjectNotExistException {
        CorpStaffVO staffVO = Xml2BeanConverter.generateCorpStaff(map);
        staffService.updateStaff(staffVO);
    }

    /**
     * 通讯录变更之删除成员
     * @param map
     */
    private void handleChangeContactDeleteUser(Map map) throws ObjectNotExistException {
        CorpStaffVO staffVO = Xml2BeanConverter.generateCorpStaff(map);
        staffService.deleteStaff(staffVO);
    }
}