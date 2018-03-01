package com.rishiqing.qywx.service.event.listener.mq;

import com.rishiqing.qywx.service.callback.PushCallbackHandler;
import com.rishiqing.qywx.service.callback.impl.PushCallbackHandlerImpl;
import com.rishiqing.qywx.service.constant.CallbackInfoType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * 将corp相关的信息推送到日事清的listener
 * @author Wallace Mao
 * Date: 2018-02-09 19:21
 */
public class PushCorpAllMqListener implements MessageListener {
    private static final Logger logger = LoggerFactory.getLogger("SERVICE_EVENT_LISTENER_LOGGER");

    @Autowired
    private PushCallbackHandler logFailPushCallbackHandler;

    @Override
    public void onMessage(Message message) {
        try {
            MapMessage mapMessage = (MapMessage)message;
            String typeString = mapMessage.getString("infoType");
            String corpId = mapMessage.getString("corpId");
            CallbackInfoType type = CallbackInfoType.getCallbackInfoType(typeString);

            //  如果type没在枚举列表中，那么报出错误
            if(null == type){
                throw new RuntimeException("type not recognized: " + typeString);
            }

            switch (type){
                case CREATE_AUTH:
                    logFailPushCallbackHandler.handleCreateCorp(corpId);
                    break;
                case CHANGE_AUTH:
                case CANCEL_AUTH:
                default:
                    break;
            }
        } catch (Exception e){
            logger.error("error in push corpAll: ", e);
        }
    }
}
