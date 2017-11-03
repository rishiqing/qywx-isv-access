package com.rishiqing.qywx.service.demo;

import com.google.common.eventbus.AsyncEventBus;
import com.rishiqing.qywx.service.event.message.CorpSuiteMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class DemoServiceImpl implements DemoService {
    private static final Logger webLogger = LoggerFactory.getLogger("WEB_LOGGER");
    private static final Logger serviceLogger = LoggerFactory.getLogger("SERVICE_LOGGER");
    private static final Logger classLogger = LoggerFactory.getLogger(DemoServiceImpl.class);

    @Autowired
    private AsyncEventBus asyncFetchDeptAndStaffEventBus;

    public String printHelloWorld() {
        webLogger.info("this is webLogger from DemoService printHelloWorld");
        serviceLogger.info("this is serviceLogger from DemoService printHelloWorld");
        classLogger.error("this is classLogger from DemoService printHelloWorld");
        return "hello";
    }

    @Override
    public void sendAsyncEvent() {
        String corpId = "wxec002534a59ea2e7";
        String suiteKey = "tj146dbe5cecf74725";
        CorpSuiteMessage message = new CorpSuiteMessage();
        message.setSuiteKey(suiteKey);
        message.setCorpId(corpId);
        asyncFetchDeptAndStaffEventBus.post(message);
    }
}
