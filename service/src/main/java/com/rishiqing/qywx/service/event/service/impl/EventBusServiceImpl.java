package com.rishiqing.qywx.service.event.service.impl;

import com.google.common.eventbus.AsyncEventBus;
import com.rishiqing.qywx.service.event.message.CorpChargeMessage;
import com.rishiqing.qywx.service.event.message.CorpRefundMessage;
import com.rishiqing.qywx.service.event.message.CorpSuiteMessage;
import com.rishiqing.qywx.service.event.message.CorpTrialMessage;
import com.rishiqing.qywx.service.event.service.EventBusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @author Wallace Mao
 * Date: 2018-04-11 22:20
 */
public class EventBusServiceImpl implements EventBusService {
    @Autowired
    @Qualifier("asyncFetchCorpAllEventBus")
    private AsyncEventBus asyncFetchDeptAndStaffEventBus;
    @Autowired
    @Qualifier("asyncDoChargeEventBus")
    private AsyncEventBus asyncDoChargeEventBus;
    @Autowired
    @Qualifier("asyncDoTrialEventBus")
    private AsyncEventBus asyncDoTrialEventBus;
    @Autowired
    @Qualifier("asyncDoRefundEventBus")
    private AsyncEventBus asyncDoRefundEventBus;

    @Override
    public void sendToFetchCorpAll(String corpId) {
        CorpSuiteMessage msg = new CorpSuiteMessage();
        msg.setCorpId(corpId);
        asyncFetchDeptAndStaffEventBus.post(msg);
    }

    @Override
    public void sendToChargeCorp(String orderId) {
        CorpChargeMessage msg = new CorpChargeMessage();
        msg.setOrderId(orderId);
        asyncDoChargeEventBus.post(msg);
    }

    @Override
    public void sendToTrialCorp(String corpId) {
        CorpTrialMessage msg = new CorpTrialMessage();
        msg.setCorpId(corpId);
        asyncDoTrialEventBus.post(msg);
    }

    @Override
    public void sendToRefundCorp(String orderId) {
        CorpRefundMessage msg = new CorpRefundMessage();
        msg.setOrderId(orderId);
        asyncDoRefundEventBus.post(msg);
    }
}
