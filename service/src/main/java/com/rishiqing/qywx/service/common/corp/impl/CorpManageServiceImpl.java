package com.rishiqing.qywx.service.common.corp.impl;

import com.alibaba.fastjson.JSONObject;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.rishiqing.qywx.dao.mapper.corp.CorpDao;
import com.rishiqing.qywx.service.common.corp.CorpAppManageService;
import com.rishiqing.qywx.service.common.corp.CorpManageService;
import com.rishiqing.qywx.service.common.corp.CorpSuiteManageService;
import com.rishiqing.qywx.service.common.corp.CorpTokenManageService;
import com.rishiqing.qywx.service.constant.ServiceConstant;
import com.rishiqing.qywx.service.exception.HttpException;
import com.rishiqing.qywx.service.exception.SuiteAccessTokenExpiredException;
import com.rishiqing.qywx.service.model.corp.CorpAppVO;
import com.rishiqing.qywx.service.model.corp.CorpSuiteVO;
import com.rishiqing.qywx.service.model.corp.CorpTokenVO;
import com.rishiqing.qywx.service.model.corp.CorpVO;
import com.rishiqing.qywx.service.model.corp.helper.CorpConverter;
import com.rishiqing.qywx.service.model.isv.SuiteTokenVO;
import com.rishiqing.qywx.service.util.http.HttpUtil;
import com.rishiqing.qywx.service.util.http.converter.Json2BeanConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CorpManageServiceImpl implements CorpManageService {
    private static final Logger logger = LoggerFactory.getLogger("CORP_MANAGE_LOGGER");
    @Autowired
    private CorpDao corpDao;

    @Override
    public void saveOrUpdateCorp(CorpVO corpVO) {
        corpDao.saveOrUpdateCorp(
                CorpConverter.corpVO2CorpDO(corpVO)
        );
    }

    @Override
    public void markRemoveCorp(String corpId, Boolean authCanceled) {
        corpDao.updateCorpSetAuthCanceled(corpId, authCanceled);
    }
}
