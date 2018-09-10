package com.rishiqing.qywx.service.callback.impl;

import com.rishiqing.qywx.service.callback.FetchCallbackHandler;
import com.rishiqing.qywx.service.callback.impl.FetchCallbackHandlerImpl;
import com.rishiqing.qywx.service.common.fail.CallbackFailService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @author Wallace Mao
 * Date: 2018-03-01 18:22
 */
public class RedoFailFetchCallbackHandlerImpl implements FetchCallbackHandler {
    @Autowired
    private FetchCallbackHandler fetchCallbackHandler;

    @Override
    public void handleFetchCorp(String corpId) {
        fetchCallbackHandler.handleFetchCorp(corpId);
    }

    @Override
    public void handleChangeContactCreateDept(Map map) {
        fetchCallbackHandler.handleChangeContactCreateDept(map);
    }

    @Override
    public void handleChangeContactUpdateDept(Map map) {
        fetchCallbackHandler.handleChangeContactUpdateDept(map);
    }

    @Override
    public void handleChangeContactDeleteDept(Map map) {
        fetchCallbackHandler.handleChangeContactDeleteDept(map);
    }

    @Override
    public void handleChangeContactCreateUser(Map map) {
        fetchCallbackHandler.handleChangeContactCreateUser(map);
    }

    @Override
    public void handleChangeContactUpdateUser(Map map) {
        fetchCallbackHandler.handleChangeContactUpdateUser(map);
    }

    @Override
    public void handleChangeContactDeleteUser(Map map) {
        fetchCallbackHandler.handleChangeContactDeleteUser(map);
    }
}
