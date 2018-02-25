package com.rishiqing.qywx.service.event.listener;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.rishiqing.qywx.service.biz.corp.DeptService;
import com.rishiqing.qywx.service.biz.corp.StaffService;
import com.rishiqing.qywx.service.common.corp.CorpAppManageService;
import com.rishiqing.qywx.service.common.corp.CorpManageService;
import com.rishiqing.qywx.service.common.corp.CorpTokenManageService;
import com.rishiqing.qywx.service.common.isv.SuiteTokenManageService;
import com.rishiqing.qywx.service.constant.CallbackInfoType;
import com.rishiqing.qywx.service.event.message.CorpSuiteMessage;
import com.rishiqing.common.exception.HttpException;
import com.rishiqing.qywx.service.event.service.AsyncService;
import com.rishiqing.qywx.service.model.corp.CorpAppVO;
import com.rishiqing.qywx.service.model.corp.CorpTokenVO;
import com.rishiqing.qywx.service.model.corp.CorpVO;
import com.rishiqing.qywx.service.model.isv.SuiteTokenVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class FetchCorpAllListener implements EventListener {
    private static final Logger logger = LoggerFactory.getLogger("SERVICE_EVENT_LISTENER_LOGGER");
    @Autowired
    private CorpManageService corpManageService;
    @Autowired
    private SuiteTokenManageService suiteTokenManageService;
    @Autowired
    private CorpAppManageService corpAppManageService;
    @Autowired
    private CorpTokenManageService corpTokenManageService;
    @Autowired
    private DeptService deptService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private AsyncService asyncService;
    @Subscribe
    @AllowConcurrentEvents  //  event并行执行
    public void listenFetchDeptAndStaff(CorpSuiteMessage corpSuiteMessage){
        String suiteKey = corpSuiteMessage.getSuiteKey();
        String corpId = corpSuiteMessage.getCorpId();

        SuiteTokenVO suiteTokenVO = suiteTokenManageService.getSuiteToken(suiteKey);
        CorpAppVO corpAppVO = corpAppManageService.getCorpAppBySuiteKeyAndCorpId(suiteKey, corpId);
        CorpTokenVO corpTokenVO = corpTokenManageService.getCorpToken(suiteKey, corpId);
        CorpVO corpVO = corpManageService.getCorpByCorpId(corpId);
        try {
            deptService.fetchAndSaveDeptInfo(corpTokenVO, null);
            staffService.fetchAndSaveStaffList(corpTokenVO, null);
            staffService.fetchAndSaveAdminList(suiteTokenVO, corpAppVO);
            //  成功后通知同步日事清
            asyncService.sendToPushCorpAuthCallback(corpVO, CallbackInfoType.CREATE_AUTH, null);
        } catch (HttpException | UnirestException e) {
            logger.error("HttpException or UnirestException fetchDeptAndStaff error, trying to fetch later", e);
        } catch (Exception e) {
            logger.error("fetchDeptAndStaff error, trying", e);
        }
    }
}
