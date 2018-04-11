package com.rishiqing.qywx.service.biz.alert.job;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rishiqing.qywx.service.biz.alert.model.converter.TodoAlertConverter;
import com.rishiqing.qywx.service.common.message.SendMessageService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.XmlWebApplicationContext;

import java.util.Map;

/**
 * @author Wallace Mao
 * Date: 2018-04-10 15:51
 */
public class AlertJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger("WEB_ALERT_LOGGER");

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            JobDetail detail  = jobExecutionContext.getJobDetail();
            Trigger trigger = jobExecutionContext.getTrigger();
            JobKey key = detail.getKey();
            logger.debug("job are going to execute: {}", key);

            XmlWebApplicationContext xmlWebApplicationContext = (XmlWebApplicationContext) jobExecutionContext.getScheduler().getContext().get("applicationContextKey");
            SendMessageService sendMessageService = (SendMessageService) xmlWebApplicationContext.getBean("sendMessageService");
            Scheduler scheduler = (Scheduler) xmlWebApplicationContext.getBean("quartzAlertScheduler");

            JobDataMap dataMap = detail.getJobDataMap();
            String corpId = dataMap.getString("corpId");
            String userIdList = dataMap.getString("userIdList");
            String msgContent = dataMap.getString("msgContent");
            String msgType = dataMap.getString("msgType");

            Map map = TodoAlertConverter.json2SendMessageMap(userIdList, msgType, JSONObject.parseObject(msgContent));

            sendMessageService.sendCorpMessage(corpId, map);

            //  如果不会再触发了，那么就删除jobDetail和trigger
            Boolean isAgain = trigger.mayFireAgain();
            System.out.println("......may fire again......" + isAgain);
            if(!isAgain){
                scheduler.deleteJob(key);
            }
        } catch (SchedulerException e) {
            logger.error("alert scheduler error", e);
        } catch (Exception e) {
            logger.error("alert error", e);
        }
    }
}
