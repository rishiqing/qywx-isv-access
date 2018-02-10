package com.rishiqing.qywx.service.event.listener.mq;

import com.rishiqing.qywx.service.biz.rsq.RsqCorpService;
import com.rishiqing.qywx.service.common.corp.CorpManageService;
import com.rishiqing.qywx.service.model.corp.CorpVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.Date;

/**
 * 将corp相关的信息推送到日事清的listener
 * @author Wallace Mao
 * Date: 2018-02-09 19:21
 */
public class PushCorpAllMqListener implements MessageListener {
    private static final Logger logger = LoggerFactory.getLogger("SERVICE_EVENT_LISTENER_LOGGER");

    @Autowired
    private CorpManageService corpManageService;
    @Autowired
    private RsqCorpService rsqCorpService;

    @Override
    public void onMessage(Message message) {
        try {
            MapMessage mapMessage = (MapMessage)message;
            String corpId = mapMessage.getString("corpId");
            CorpVO corpVO = corpManageService.getCorpByCorpId(corpId);
            rsqCorpService.pushAndCreateCorpAll(corpVO);
        } catch (Exception e){
            logger.error("error in push corpAll: ", e);
        }
    }
}
