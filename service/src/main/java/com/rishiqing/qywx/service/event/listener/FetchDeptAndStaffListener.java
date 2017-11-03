package com.rishiqing.qywx.service.event.listener;

import com.google.common.eventbus.Subscribe;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.rishiqing.qywx.service.biz.corp.DeptService;
import com.rishiqing.qywx.service.biz.corp.StaffService;
import com.rishiqing.qywx.service.common.corp.CorpTokenManageService;
import com.rishiqing.qywx.service.demo.DemoService;
import com.rishiqing.qywx.service.event.message.CorpSuiteMessage;
import com.rishiqing.qywx.service.exception.HttpException;
import com.rishiqing.qywx.service.model.corp.CorpTokenVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class FetchDeptAndStaffListener implements EventListener {
    private static final Logger logger = LoggerFactory.getLogger("SERVICE_EVENT_LISTENER_LOGGER");
    @Autowired
    private CorpTokenManageService corpTokenManageService;
    @Autowired
    private DeptService deptService;
    @Autowired
    private StaffService staffService;
    @Subscribe
    public void listenFetchDeptAndStaff(CorpSuiteMessage corpSuiteMessage){
        String suiteKey = corpSuiteMessage.getSuiteKey();
        String corpId = corpSuiteMessage.getCorpId();

        CorpTokenVO corpTokenVO = corpTokenManageService.getCorpToken(suiteKey, corpId);
        try {
            deptService.fetchAndSaveDeptInfo(corpTokenVO, null);
            staffService.fetchAndSaveStaffList(corpTokenVO, null);
        } catch (HttpException | UnirestException e) {
            logger.error("HttpException or UnirestException fetchDeptAndStaff error, trying to fetch later");
            e.printStackTrace();
        } catch (Exception e) {
            logger.error("fetchDeptAndStaff error, trying");
            e.printStackTrace();
        }
    }
}
