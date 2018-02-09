package com.rishiqing.qywx.service.event.listener;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.rishiqing.qywx.service.biz.corp.DeptService;
import com.rishiqing.qywx.service.biz.corp.StaffService;
import com.rishiqing.qywx.service.common.corp.CorpAppManageService;
import com.rishiqing.qywx.service.common.corp.CorpTokenManageService;
import com.rishiqing.qywx.service.common.isv.SuiteTokenManageService;
import com.rishiqing.qywx.service.event.message.CorpSuiteMessage;
import com.rishiqing.common.exception.HttpException;
import com.rishiqing.qywx.service.model.corp.CorpAppVO;
import com.rishiqing.qywx.service.model.corp.CorpTokenVO;
import com.rishiqing.qywx.service.model.isv.SuiteTokenVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class FetchDeptAndStaffListener implements EventListener {
    private static final Logger logger = LoggerFactory.getLogger("SERVICE_EVENT_LISTENER_LOGGER");
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
    @Subscribe
    @AllowConcurrentEvents  //  event并行执行
    public void listenFetchDeptAndStaff(CorpSuiteMessage corpSuiteMessage){
        String suiteKey = corpSuiteMessage.getSuiteKey();
        String corpId = corpSuiteMessage.getCorpId();

        SuiteTokenVO suiteTokenVO = suiteTokenManageService.getSuiteToken(suiteKey);
        CorpAppVO corpAppVO = corpAppManageService.getCorpAppBySuiteKeyAndCorpId(suiteKey, corpId);
        CorpTokenVO corpTokenVO = corpTokenManageService.getCorpToken(suiteKey, corpId);
        try {
            deptService.fetchAndSaveDeptInfo(corpTokenVO, null);
            staffService.fetchAndSaveStaffList(corpTokenVO, null);
            staffService.fetchAndSaveAdminList(suiteTokenVO, corpAppVO);
        } catch (HttpException | UnirestException e) {
            logger.error("HttpException or UnirestException fetchDeptAndStaff error, trying to fetch later", e);
        } catch (Exception e) {
            logger.error("fetchDeptAndStaff error, trying", e);
        }
    }
}
