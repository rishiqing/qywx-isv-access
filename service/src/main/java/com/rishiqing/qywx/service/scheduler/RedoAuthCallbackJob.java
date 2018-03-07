package com.rishiqing.qywx.service.scheduler;

import com.rishiqing.qywx.dao.model.fail.FailAuthCallbackDO;
import com.rishiqing.qywx.service.callback.FetchCallbackHandler;
import com.rishiqing.qywx.service.callback.PushCallbackHandler;
import com.rishiqing.qywx.service.common.fail.CallbackFailService;
import com.rishiqing.qywx.service.constant.CallbackFailType;
import com.rishiqing.qywx.service.callback.impl.PushCallbackHandlerImpl;
import com.rishiqing.qywx.service.exception.CallbackException;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.support.XmlWebApplicationContext;

import java.util.Date;
import java.util.List;

/**
 * 重新激活应用
 * @author Wallace Mao
 * Date: 2018-03-01 15:40
 */
public class RedoAuthCallbackJob extends QuartzJobBean {
    private static final Logger logger = LoggerFactory.getLogger("SERVICE_JOB_AUTH_CALLBACK_LOGGER");

    @Override
    public void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.debug("-------------test RedoAuthCallbackJob----------{}", new Date());
        try {
            XmlWebApplicationContext xmlWebApplicationContext = (XmlWebApplicationContext) jobExecutionContext.getScheduler().getContext().get("applicationContextKey");
            CallbackFailService service = (CallbackFailService)xmlWebApplicationContext.getBean("callbackFailService");
            FetchCallbackHandler fetchHandler = (FetchCallbackHandler) xmlWebApplicationContext.getBean("redoFailFetchCallbackHandler");
            PushCallbackHandler pushHandler = (PushCallbackHandler)xmlWebApplicationContext.getBean("redoFailPushCallbackHandler");
            List<FailAuthCallbackDO> list = service.listFailAuthCallback();
            for(FailAuthCallbackDO authFail : list){
                CallbackFailType type = CallbackFailType.getCallbackFailType(authFail.getFailType());
                switch(type){
                    case AUTH_CALLBACK_FAIL_SAVE_NEW_CORP:
                        fetchHandler.handleFetchCorp(authFail.getCorpId(), authFail.getFailNote());
                        //  成功后删除
                        service.deleteFailAuthCallback(authFail.getId());
                        break;
                    case AUTH_CALLBACK_FAIL_PUSH_NEW_CORP:
                        pushHandler.handleCreateCorp(authFail.getCorpId());
                        //  成功后删除
                        service.deleteFailAuthCallback(authFail.getId());
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            logger.error("redo auth callback fail", e);
        }
    }
}
