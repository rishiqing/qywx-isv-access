package com.rishiqing.qywx.service.common.fail.impl;

import com.rishiqing.qywx.dao.mapper.fail.FailAuthCallbackDao;
import com.rishiqing.qywx.dao.mapper.fail.FailContactCallbackDao;
import com.rishiqing.qywx.dao.model.fail.FailAuthCallbackDO;
import com.rishiqing.qywx.dao.model.fail.FailContactCallbackDO;
import com.rishiqing.qywx.service.common.fail.CallbackFailService;
import com.rishiqing.qywx.service.common.isv.GlobalSuite;
import com.rishiqing.qywx.service.constant.CallbackChangeType;
import com.rishiqing.qywx.service.constant.CallbackFailType;
import com.rishiqing.qywx.service.constant.CallbackInfoType;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Wallace Mao
 * Date: 2018-02-28 16:20
 */
public class CallbackFailServiceImpl implements CallbackFailService {
    @Autowired
    private GlobalSuite suite;
    @Autowired
    private FailAuthCallbackDao failAuthCallbackDao;
    @Autowired
    private FailContactCallbackDao failContactCallbackDao;

    @Override
    public void save(String corpId, CallbackFailType failType, CallbackInfoType infoType, CallbackChangeType changeType, String content) {
        String suiteKey = suite.getSuiteKey();

        switch (failType){
            case AUTH_CALLBACK_FAIL_SAVE_NEW_CORP:
            case AUTH_CALLBACK_FAIL_PUSH_NEW_CORP:
                FailAuthCallbackDO failAuthCallbackDO = new FailAuthCallbackDO();
                failAuthCallbackDO.setSuiteKey(suiteKey);
                failAuthCallbackDO.setCorpId(corpId);
                failAuthCallbackDO.setFailType(failType.getKey());
                failAuthCallbackDO.setInfoType(infoType.getKey());
                failAuthCallbackDO.setFailNote(content);
                failAuthCallbackDao.saveOrUpdateFailAuthCallback(failAuthCallbackDO);
                break;
            case CONTACT_CALLBACK_FAIL_SAVE_COMMON:
            case CONTACT_CALLBACK_FAIL_PUSH_COMMON:
                FailContactCallbackDO failContactCallbackDO = new FailContactCallbackDO();
                failContactCallbackDO.setSuiteKey(suiteKey);
                failContactCallbackDO.setCorpId(corpId);
                failContactCallbackDO.setFailType(failType.getKey());
                failContactCallbackDO.setChangeType(changeType.getKey());
                failContactCallbackDO.setEventMsg(content);
                failContactCallbackDao.saveOrUpdateFailContactCallback(failContactCallbackDO);
                break;
            default:
                break;
        }
    }
}
