package com.rishiqing.qywx.service.event.listener.mq;

import com.rishiqing.common.exception.NotSupportedException;
import com.rishiqing.qywx.service.biz.rsq.RsqDeptService;
import com.rishiqing.qywx.service.biz.rsq.RsqStaffService;
import com.rishiqing.qywx.service.common.corp.CorpDeptManageService;
import com.rishiqing.qywx.service.common.corp.CorpManageService;
import com.rishiqing.qywx.service.common.corp.CorpStaffManageService;
import com.rishiqing.qywx.service.constant.CallbackChangeType;
import com.rishiqing.qywx.service.model.corp.CorpDeptVO;
import com.rishiqing.qywx.service.model.corp.CorpStaffVO;
import com.rishiqing.qywx.service.model.corp.CorpVO;
import com.rishiqing.qywx.service.util.http.converter.Xml2BeanConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.List;
import java.util.Map;

/**
 * 处理企业微信接收到的回调消息的mq listener
 * @author Wallace Mao
 * Date: 2018-02-10 14:20
 */
public class PushCorpCallbackMqListener implements MessageListener {
    private static final Logger logger = LoggerFactory.getLogger("SERVICE_EVENT_LISTENER_LOGGER");

    @Autowired
    private PushCorpHandler pushCorpHandler;

    @Override
    public void onMessage(Message message) {
        MapMessage mapMessage = (MapMessage)message;
        try {
            String corpId = mapMessage.getString("corpId");
            CallbackChangeType type = CallbackChangeType.getCallbackChangeType(mapMessage.getString("type"));
            Map contentMap = (Map)mapMessage.getObject("content");

            //  如果type没在枚举列表中，那么报出错误
            if(null == type){
                throw new RuntimeException("type not recognized: " + mapMessage.getString("type"));
            }

            switch (type){
                case CREATE_PARTY:
                    pushCorpHandler.handleCreateDept(corpId, contentMap);
                    break;
                case UPDATE_PARTY:
                    pushCorpHandler.handleUpdateDept(corpId, contentMap);
                    break;
                case DELETE_PARTY:
                    pushCorpHandler.handleDeleteDept(corpId, contentMap);
                    break;
                case CREATE_USER:
                    pushCorpHandler.handleCreateUser(corpId, contentMap);
                    break;
                case UPDATE_USER:
                    pushCorpHandler.handleUpdateUser(corpId, contentMap);
                    break;
                case DELETE_USER:
                    pushCorpHandler.handleDeleteUser(corpId, contentMap);
                    break;
                case UPDATE_TAG:
                    throw new NotSupportedException("change type UPDATE_TAG not supported now");
                default:
                    break;
            }
        } catch (Exception e) {
            logger.error("error in push corpAll: ", e);
        }
    }
}
