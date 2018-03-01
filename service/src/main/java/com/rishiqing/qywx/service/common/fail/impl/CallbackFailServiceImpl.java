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
import com.rishiqing.qywx.service.constant.ServiceConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

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

    @Override
    public void deleteFailAuthCallback(Long id) {
        failAuthCallbackDao.removeFailAuthCallbackById(id);
    }

    @Override
    public void deleteFailContactCallback(Long id) {
        failContactCallbackDao.removeFailContactCallbackById(id);
    }

    @Override
    public List<FailAuthCallbackDO> listFailAuthCallback() {
        return failAuthCallbackDao.listFailAuthCallbackWithLimit(ServiceConstant.FAIL_AUTH_CALLBACK_LIST_LIMIT);
    }

    @Override
    public List<FailContactCallbackDO> listFailContactCallback() {
        return failContactCallbackDao.listFailContactCallbackWithLimit(ServiceConstant.FAIL_CONTACT_CALLBACK_LIST_LIMIT);
    }
}
