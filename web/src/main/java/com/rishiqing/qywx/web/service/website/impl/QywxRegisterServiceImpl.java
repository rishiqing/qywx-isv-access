package com.rishiqing.qywx.web.service.website.impl;

import com.alibaba.fastjson.JSONObject;
import com.rishiqing.qywx.service.common.isv.GlobalSuite;
import com.rishiqing.qywx.service.common.isv.IsvManageService;
import com.rishiqing.qywx.service.model.isv.IsvVO;
import com.rishiqing.qywx.service.model.isv.SuiteVO;
import com.rishiqing.qywx.service.model.website.RegisterCodeVO;
import com.rishiqing.qywx.service.model.website.RegisterInfoVO;
import com.rishiqing.qywx.service.util.http.HttpUtilIsv;
import com.rishiqing.qywx.service.util.http.converter.Json2BeanConverter;
import com.rishiqing.qywx.web.service.website.QywxRegisterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @author Wallace Mao
 * Date: 2018-05-12 21:10
 */
public class QywxRegisterServiceImpl implements QywxRegisterService {
    private static Logger logger = LoggerFactory.getLogger("CONSOLE_LOGGER");

    @Autowired
    private HttpUtilIsv httpUtilIsv;
    @Autowired
    private IsvManageService isvManageService;
    @Autowired
    private Map isvGlobal;
    @Autowired
    private GlobalSuite suite;

    @Override
    public String fetchRegisterCode() {
        String corpId = suite.getCorpId();
        IsvVO isv = isvManageService.getIsv(corpId);
        RegisterInfoVO reg = new RegisterInfoVO();
        reg.setTemplateId((String)isvGlobal.get("isvRegisterTemplateId"));
        JSONObject json = httpUtilIsv.getRegisterCode(isv, reg);

        RegisterCodeVO regCode = Json2BeanConverter.generateRegisterCode(json);
        return regCode.getRegisterCode();
    }
}
