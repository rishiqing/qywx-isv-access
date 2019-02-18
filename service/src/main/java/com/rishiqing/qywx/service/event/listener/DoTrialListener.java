package com.rishiqing.qywx.service.event.listener;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import com.rishiqing.qywx.service.biz.order.OrderService;
import com.rishiqing.qywx.service.event.message.CorpTrialMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class DoTrialListener implements EventListener {
    private static final Logger logger = LoggerFactory.getLogger("SERVICE_EVENT_FETCH_LISTENER_LOGGER");

    @Autowired
    private OrderService orderService;

    @Subscribe
    @AllowConcurrentEvents  //  event并行执行
    public void listen(CorpTrialMessage msg){
        try {
            String corpId = msg.getCorpId();
            orderService.doTrialByCorp(corpId);
        } catch (Exception e) {
            logger.error("doTrialByCorp", e);
        }
    }
}
