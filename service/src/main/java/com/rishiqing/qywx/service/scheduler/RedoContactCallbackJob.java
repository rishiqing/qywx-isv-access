package com.rishiqing.qywx.service.scheduler;

import com.alibaba.fastjson.JSON;
import com.rishiqing.qywx.dao.model.fail.FailContactCallbackDO;
import com.rishiqing.qywx.service.callback.FetchCallbackHandler;
import com.rishiqing.qywx.service.callback.PushCallbackHandler;
import com.rishiqing.qywx.service.common.fail.CallbackFailService;
import com.rishiqing.qywx.service.constant.CallbackChangeType;
import com.rishiqing.qywx.service.constant.CallbackFailType;
import com.rishiqing.qywx.service.exception.CallbackException;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.support.XmlWebApplicationContext;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 通讯录变更的重做方法
 * @author Wallace Mao
 * Date: 2018-03-01 15:40
 */
public class RedoContactCallbackJob extends QuartzJobBean {
    private static final Logger logger = LoggerFactory.getLogger("SERVICE_JOB_CONTACT_CALLBACK_LOGGER");

    @Override
    public void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.debug("========test RedoContactCallbackJob========" + new Date());
        try {
            XmlWebApplicationContext xmlWebApplicationContext = (XmlWebApplicationContext) jobExecutionContext.getScheduler().getContext().get("applicationContextKey");
            CallbackFailService service = (CallbackFailService)xmlWebApplicationContext.getBean("callbackFailService");
            FetchCallbackHandler fetchHandler = (FetchCallbackHandler)xmlWebApplicationContext.getBean("redoFailFetchCallbackHandler");
            PushCallbackHandler pushHandler = (PushCallbackHandler)xmlWebApplicationContext.getBean("redoFailPushCallbackHandler");

            List<FailContactCallbackDO> list = service.listFailContactCallback();
            for(FailContactCallbackDO fail : list){
                CallbackFailType type = CallbackFailType.getCallbackFailType(fail.getFailType());
                switch(type){
                    case CONTACT_CALLBACK_FAIL_SAVE_COMMON:
                        handleFetchContactCallback(fetchHandler, fail);
                        service.deleteFailContactCallback(fail.getId());
                        break;
                    case CONTACT_CALLBACK_FAIL_PUSH_COMMON:
                        handlePushContactCallback(pushHandler, fail);
                        service.deleteFailContactCallback(fail.getId());
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            logger.error("redo contact callback fail", e);
        }
    }

    private void handleFetchContactCallback(FetchCallbackHandler handler, FailContactCallbackDO fail){
        CallbackChangeType changeType = CallbackChangeType.getCallbackChangeType(fail.getChangeType());
        Map map = (Map) JSON.parse(fail.getEventMsg());
        switch (changeType){
            case CREATE_PARTY:
                handler.handleChangeContactCreateDept(map);
                break;
            case UPDATE_PARTY:
                handler.handleChangeContactUpdateDept(map);
                break;
            case DELETE_PARTY:
                handler.handleChangeContactDeleteDept(map);
                break;
            case CREATE_USER:
                handler.handleChangeContactCreateUser(map);
                break;
            case UPDATE_USER:
                handler.handleChangeContactUpdateUser(map);
                break;
            case DELETE_USER:
                handler.handleChangeContactDeleteUser(map);
                break;
            default:
                throw new CallbackException("contact change, changeType not handled: " + changeType);
        }
    }

    private void handlePushContactCallback(PushCallbackHandler handler, FailContactCallbackDO fail){
        CallbackChangeType changeType = CallbackChangeType.getCallbackChangeType(fail.getChangeType());
        Map map = (Map) JSON.parse(fail.getEventMsg());
        String corpId = fail.getCorpId();
        switch (changeType){
            case CREATE_PARTY:
                handler.handleCreateDept(corpId, map);
                break;
            case UPDATE_PARTY:
                handler.handleUpdateDept(corpId, map);
                break;
            case DELETE_PARTY:
                handler.handleDeleteDept(corpId, map);
                break;
            case CREATE_USER:
                handler.handleCreateUser(corpId, map);
                break;
            case UPDATE_USER:
                handler.handleUpdateUser(corpId, map);
                break;
            case DELETE_USER:
                handler.handleDeleteUser(corpId, map);
                break;
            default:
                throw new CallbackException("contact change, changeType not handled: " + changeType);
        }
    }
}
