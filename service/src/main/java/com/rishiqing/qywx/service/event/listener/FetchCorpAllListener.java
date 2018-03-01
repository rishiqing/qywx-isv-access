package com.rishiqing.qywx.service.event.listener;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import com.rishiqing.qywx.service.callback.FetchCallbackHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class FetchCorpAllListener implements EventListener {
    private static final Logger logger = LoggerFactory.getLogger("SERVICE_EVENT_LISTENER_LOGGER");
    @Autowired
    private FetchCallbackHandler logFailFetchCallbackHandler;

    @Subscribe
    @AllowConcurrentEvents  //  event并行执行
    public void listenFetchDeptAndStaff(String permanentCode){
        try {
            logFailFetchCallbackHandler.handleFetchCorp(permanentCode);
        } catch (Exception e) {
            logger.error("fetchCorpAllError", e);
        }
    }
}
