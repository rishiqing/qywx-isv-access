package com.rishiqing.qywx.service.callback.impl;

import com.alibaba.fastjson.JSON;
import com.rishiqing.qywx.service.callback.FetchCallbackHandler;
import com.rishiqing.qywx.service.common.fail.CallbackFailService;
import com.rishiqing.qywx.service.constant.CallbackChangeType;
import com.rishiqing.qywx.service.constant.CallbackFailType;
import com.rishiqing.qywx.service.constant.CallbackInfoType;
import com.rishiqing.qywx.service.exception.ObjectNotExistException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * fetchCallbackHandler的包装类，保存错误日志
 * @author Wallace Mao
 * Date: 2018-03-01 18:22
 */
public class LogFailFetchCallbackHandlerImpl implements FetchCallbackHandler {
    @Autowired
    private FetchCallbackHandler fetchCallbackHandler;
    @Autowired
    private CallbackFailService callbackFailService;

    @Override
    public void handleFetchCorp(String permanentCode) {
        try {
            fetchCallbackHandler.handleFetchCorp(permanentCode);
        } catch (Exception e) {
            //  加入重试
            callbackFailService.save(permanentCode,
                    CallbackFailType.AUTH_CALLBACK_FAIL_SAVE_NEW_CORP,
                    CallbackInfoType.CREATE_AUTH,
                    null,
                    permanentCode);
            throw e;
        }
    }

    @Override
    public void handleChangeContactCreateDept(Map map) {
        try {
            fetchCallbackHandler.handleChangeContactCreateDept(map);
        } catch (Exception e) {
            String jsonSting = JSON.toJSONString(map);
            callbackFailService.save((String)map.get("AuthCorpId"),
                    CallbackFailType.CONTACT_CALLBACK_FAIL_SAVE_COMMON,
                    CallbackInfoType.CHANGE_CONTACT,
                    CallbackChangeType.CREATE_PARTY,
                    jsonSting);
            throw e;
        }
    }

    @Override
    public void handleChangeContactUpdateDept(Map map) {
        try {
            fetchCallbackHandler.handleChangeContactUpdateDept(map);
        } catch (Exception e) {
            String jsonSting = JSON.toJSONString(map);
            callbackFailService.save((String)map.get("AuthCorpId"),
                    CallbackFailType.CONTACT_CALLBACK_FAIL_SAVE_COMMON,
                    CallbackInfoType.CHANGE_CONTACT,
                    CallbackChangeType.UPDATE_PARTY,
                    jsonSting);
            throw e;
        }
    }

    @Override
    public void handleChangeContactDeleteDept(Map map) {
        try {
            fetchCallbackHandler.handleChangeContactDeleteDept(map);
        } catch (Exception e) {
            String jsonSting = JSON.toJSONString(map);
            callbackFailService.save((String)map.get("AuthCorpId"),
                    CallbackFailType.CONTACT_CALLBACK_FAIL_SAVE_COMMON,
                    CallbackInfoType.CHANGE_CONTACT,
                    CallbackChangeType.DELETE_PARTY,
                    jsonSting);
            throw e;
        }
    }

    @Override
    public void handleChangeContactCreateUser(Map map) {
        try {
            fetchCallbackHandler.handleChangeContactCreateUser(map);
        } catch (Exception e) {
            String jsonSting = JSON.toJSONString(map);
            callbackFailService.save((String)map.get("AuthCorpId"),
                    CallbackFailType.CONTACT_CALLBACK_FAIL_SAVE_COMMON,
                    CallbackInfoType.CHANGE_CONTACT,
                    CallbackChangeType.CREATE_USER,
                    jsonSting);
            throw e;
        }
    }

    @Override
    public void handleChangeContactUpdateUser(Map map) {
        try {
            fetchCallbackHandler.handleChangeContactUpdateUser(map);
        } catch (Exception e) {
            String jsonSting = JSON.toJSONString(map);
            callbackFailService.save((String)map.get("AuthCorpId"),
                    CallbackFailType.CONTACT_CALLBACK_FAIL_SAVE_COMMON,
                    CallbackInfoType.CHANGE_CONTACT,
                    CallbackChangeType.UPDATE_USER,
                    jsonSting);
            throw e;
        }
    }

    @Override
    public void handleChangeContactDeleteUser(Map map) {
        try {
            fetchCallbackHandler.handleChangeContactDeleteUser(map);
        } catch (ObjectNotExistException e) {
            String jsonSting = JSON.toJSONString(map);
            callbackFailService.save((String)map.get("AuthCorpId"),
                    CallbackFailType.CONTACT_CALLBACK_FAIL_SAVE_COMMON,
                    CallbackInfoType.CHANGE_CONTACT,
                    CallbackChangeType.DELETE_USER,
                    jsonSting);
            throw e;
        }
    }
}
