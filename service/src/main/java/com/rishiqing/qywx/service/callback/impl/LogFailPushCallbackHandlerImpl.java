package com.rishiqing.qywx.service.callback.impl;

import com.alibaba.fastjson.JSON;
import com.rishiqing.qywx.service.callback.PushCallbackHandler;
import com.rishiqing.qywx.service.common.fail.CallbackFailService;
import com.rishiqing.qywx.service.constant.CallbackChangeType;
import com.rishiqing.qywx.service.constant.CallbackFailType;
import com.rishiqing.qywx.service.constant.CallbackInfoType;
import com.rishiqing.qywx.service.exception.CallbackException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * pushCallbackHandler的包装类，用来保存push过程中的错误
 * @author Wallace Mao
 * Date: 2018-03-01 18:22
 */
public class LogFailPushCallbackHandlerImpl implements PushCallbackHandler {
    @Autowired
    private PushCallbackHandler pushCallbackHandler;
    @Autowired
    private CallbackFailService callbackFailService;

    @Override
    public void handlePushCorp(String corpId) {
        try {
            pushCallbackHandler.handlePushCorp(corpId);
        } catch (Exception e) {
            //  做重试
            callbackFailService.save(corpId,
                    CallbackFailType.AUTH_CALLBACK_FAIL_PUSH_NEW_CORP,
                    CallbackInfoType.CHANGE_CONTACT,
                    null,
                    null);
            throw new CallbackException(e);
        }
    }

    @Override
    public void handleCreateDept(String corpId, Map contentMap) {
        try {
            pushCallbackHandler.handleCreateDept(corpId, contentMap);
        } catch (Exception e) {
            String jsonString  = JSON.toJSONString(contentMap);
            callbackFailService.save(corpId,
                    CallbackFailType.CONTACT_CALLBACK_FAIL_PUSH_COMMON,
                    CallbackInfoType.CHANGE_CONTACT,
                    CallbackChangeType.CREATE_PARTY,
                    jsonString);
            throw new CallbackException(e);
        }
    }

    @Override
    public void handleUpdateDept(String corpId, Map contentMap) {
        try {
            pushCallbackHandler.handleUpdateDept(corpId, contentMap);
        } catch (Exception e) {
            String jsonString  = JSON.toJSONString(contentMap);
            callbackFailService.save(corpId,
                    CallbackFailType.CONTACT_CALLBACK_FAIL_PUSH_COMMON,
                    CallbackInfoType.CHANGE_CONTACT,
                    CallbackChangeType.UPDATE_PARTY,
                    jsonString);
            throw new CallbackException(e);
        }
    }

    @Override
    public void handleDeleteDept(String corpId, Map contentMap) {
        try {
            pushCallbackHandler.handleDeleteDept(corpId, contentMap);
        } catch (Exception e) {
            String jsonString  = JSON.toJSONString(contentMap);
            callbackFailService.save(corpId,
                    CallbackFailType.CONTACT_CALLBACK_FAIL_PUSH_COMMON,
                    CallbackInfoType.CHANGE_CONTACT,
                    CallbackChangeType.DELETE_PARTY,
                    jsonString);
            throw new CallbackException(e);
        }
    }

    @Override
    public void handleCreateUser(String corpId, Map contentMap) {
        try {
            pushCallbackHandler.handleCreateUser(corpId, contentMap);
        } catch (Exception e) {
            String jsonString  = JSON.toJSONString(contentMap);
            callbackFailService.save(corpId,
                    CallbackFailType.CONTACT_CALLBACK_FAIL_PUSH_COMMON,
                    CallbackInfoType.CHANGE_CONTACT,
                    CallbackChangeType.CREATE_USER,
                    jsonString);
            throw new CallbackException(e);
        }
    }

    @Override
    public void handleUpdateUser(String corpId, Map contentMap) {
        try {
            pushCallbackHandler.handleUpdateUser(corpId, contentMap);
        } catch (Exception e) {
            String jsonString  = JSON.toJSONString(contentMap);
            callbackFailService.save(corpId,
                    CallbackFailType.CONTACT_CALLBACK_FAIL_PUSH_COMMON,
                    CallbackInfoType.CHANGE_CONTACT,
                    CallbackChangeType.UPDATE_USER,
                    jsonString);
            throw new CallbackException(e);
        }
    }

    @Override
    public void handleDeleteUser(String corpId, Map contentMap) {
        try {
            pushCallbackHandler.handleDeleteUser(corpId, contentMap);
        } catch (Exception e) {
            String jsonString  = JSON.toJSONString(contentMap);
            callbackFailService.save(corpId,
                    CallbackFailType.CONTACT_CALLBACK_FAIL_PUSH_COMMON,
                    CallbackInfoType.CHANGE_CONTACT,
                    CallbackChangeType.DELETE_USER,
                    jsonString);
            throw new CallbackException(e);
        }
    }
}
