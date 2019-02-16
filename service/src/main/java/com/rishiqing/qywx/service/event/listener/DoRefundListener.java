package com.rishiqing.qywx.service.event.listener;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import com.rishiqing.qywx.service.biz.order.OrderService;
import com.rishiqing.qywx.service.event.message.CorpRefundMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class DoRefundListener implements EventListener {
    private static final Logger logger = LoggerFactory.getLogger("SERVICE_EVENT_FETCH_LISTENER_LOGGER");

    @Autowired
    private OrderService orderService;

    @Subscribe
    @AllowConcurrentEvents  //  event并行执行
    public void listen(CorpRefundMessage msg){
        try {
            String orderId = msg.getOrderId();
            orderService.doRefundByOrder(orderId);
        } catch (Exception e) {
            logger.error("doRefundByOrder", e);
        }
    }
}
