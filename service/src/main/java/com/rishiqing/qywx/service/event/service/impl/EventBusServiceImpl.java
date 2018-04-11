package com.rishiqing.qywx.service.event.service.impl;

import com.google.common.eventbus.AsyncEventBus;
import com.rishiqing.qywx.service.event.message.CorpSuiteMessage;
import com.rishiqing.qywx.service.event.service.EventBusService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Wallace Mao
 * Date: 2018-04-11 22:20
 */
public class EventBusServiceImpl implements EventBusService {
    @Autowired
    private AsyncEventBus asyncFetchDeptAndStaffEventBus;
    @Override
    public void sendToFetchCorpAll(String corpId, String permanentCode) {
        CorpSuiteMessage msg = new CorpSuiteMessage();
        msg.setCorpId(corpId);
        msg.setPermanentCode(permanentCode);
        asyncFetchDeptAndStaffEventBus.post(msg);
    }
}
