package com.rishiqing.qywx.service.biz.alert.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rishiqing.qywx.service.biz.alert.AlertService;
import com.rishiqing.qywx.service.biz.alert.exception.AlertException;
import com.rishiqing.qywx.service.biz.alert.job.AlertJob;
import com.rishiqing.qywx.service.biz.alert.model.TodoAlertVO;
import com.rishiqing.qywx.service.biz.alert.model.converter.TodoAlertConverter;
import com.rishiqing.qywx.service.common.message.SendMessageService;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @author Wallace Mao
 * Date: 2018-04-10 15:23
 */
public class AlertServiceImpl implements AlertService {
    @Autowired
    private Scheduler quartzAlertScheduler;

    /**
     * 设置提醒，如果之前todoId有提醒，则会覆盖
     * @param corpId
     * @param todoAlertVO
     */
    public void setAlert(String corpId, TodoAlertVO todoAlertVO){

        //  暂定一次不超过5个
        try {
            String todoId = todoAlertVO.getTodoId();
            List<Long> millsList = todoAlertVO.getMillsList();
            List<String> ruleList = todoAlertVO.getRuleList();

            String groupKeyString = "rsq-alert-" + corpId + "-" + todoId;
            //  先根据corpId和todoId，查出是否已经有计时任务存在，如果有，先统一删除
            GroupMatcher<JobKey> matcher = GroupMatcher.groupEquals("J-" + groupKeyString);
            for(JobKey jobKey : quartzAlertScheduler.getJobKeys(matcher)) {
                System.out.println("Found job identified by: " + jobKey);
                quartzAlertScheduler.deleteJob(jobKey);
            }
            Iterator it = millsList.iterator();
            Iterator itRule = ruleList.iterator();
            while (it.hasNext()) {
                Long mills = (Long)it.next();
                String remind = (String)itRule.next();
                JobKey jobKey = new JobKey("J-" + mills, "J-" + groupKeyString);
                //JobDetail currentDetail = quartzRemindScheduler.getJobDetail(jobKey);
                //if(currentDetail != null){
                //    quartzRemindScheduler.deleteJob(jobKey);
                //}

    //            String msgContent = MessageUtil.remindText(jsonContent, remind);
                String msgContent = todoAlertVO.getMsgContent().toString();

                JobDetail job = JobBuilder.newJob(AlertJob.class)
                        .withIdentity(jobKey)
                        .usingJobData("corpId", corpId)
                        .usingJobData("userIdList", todoAlertVO.getUserIdListString())
                        .usingJobData("msgType", todoAlertVO.getMsgType())
                        .usingJobData("msgContent", msgContent)
                        .build();

                Trigger trigger = TriggerBuilder.newTrigger()
                        .withIdentity("T-" + mills, "T-" + groupKeyString)
                        .startAt(new Date(mills))
                        .forJob(job)
                        .build();
                quartzAlertScheduler.scheduleJob(job, trigger);
            }
        } catch (SchedulerException e) {
            throw new AlertException(e);
        }
    }

    /**
     * 删除提醒
     * @param corpId
     * @param todoAlertVO
     */
    public void removeAlert(String corpId, TodoAlertVO todoAlertVO){
        try {
            String todoId = todoAlertVO.getTodoId();

            String groupKeyString = "rsq-alert-" + corpId + "-" + todoId;
            //  先根据corpId和todoId，查出是否已经有计时任务存在，如果有，先统一删除
            GroupMatcher<JobKey> matcher = GroupMatcher.groupEquals("J-" + groupKeyString);
            for(JobKey jobKey : quartzAlertScheduler.getJobKeys(matcher)) {
                System.out.println("Found job identified by: " + jobKey);
                quartzAlertScheduler.deleteJob(jobKey);
            }
        } catch (SchedulerException e) {
            throw new AlertException(e);
        }
    }
}
