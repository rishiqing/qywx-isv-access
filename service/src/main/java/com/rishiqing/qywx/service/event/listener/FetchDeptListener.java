package com.rishiqing.qywx.service.event.listener;

import com.google.common.eventbus.Subscribe;
import com.rishiqing.qywx.service.event.message.CorpSuiteMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FetchDeptListener implements EventListener {
    private static final Logger logger = LoggerFactory.getLogger("SERVICE_EVENT_LISTENER_LOGGER");
    @Subscribe
    public void listenFetchDept(CorpSuiteMessage corpSuiteMessage){
        System.out.println("-=====================");
        logger.info("--------corpSuiteMessage-----suiteKey:{}----corpId:{}",
                corpSuiteMessage.getSuiteKey(),
                corpSuiteMessage.getCorpId());
    }
}
