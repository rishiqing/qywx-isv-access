package com.rishiqing.qywx.service.callback.impl;

import com.rishiqing.qywx.service.callback.PushCallbackHandler;
import com.rishiqing.qywx.service.common.fail.CallbackFailService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @author Wallace Mao
 * Date: 2018-03-01 18:22
 */
public class RedoFailPushCallbackHandlerImpl implements PushCallbackHandler {
    @Autowired
    private PushCallbackHandler pushCallbackHandler;
    @Autowired
    private CallbackFailService callbackFailService;

    @Override
    public void handleCreateCorp(String corpId) {
        pushCallbackHandler.handleCreateCorp(corpId);
    }

    @Override
    public void handleCreateDept(String corpId, Map contentMap) {
        pushCallbackHandler.handleCreateDept(corpId, contentMap);
    }

    @Override
    public void handleUpdateDept(String corpId, Map contentMap) {
        pushCallbackHandler.handleUpdateDept(corpId, contentMap);
    }

    @Override
    public void handleDeleteDept(String corpId, Map contentMap) {
        pushCallbackHandler.handleDeleteDept(corpId, contentMap);
    }

    @Override
    public void handleCreateUser(String corpId, Map contentMap) {
        pushCallbackHandler.handleCreateUser(corpId, contentMap);
    }

    @Override
    public void handleUpdateUser(String corpId, Map contentMap) {
        pushCallbackHandler.handleUpdateUser(corpId, contentMap);
    }

    @Override
    public void handleDeleteUser(String corpId, Map contentMap) {
        pushCallbackHandler.handleDeleteUser(corpId, contentMap);
    }
}
