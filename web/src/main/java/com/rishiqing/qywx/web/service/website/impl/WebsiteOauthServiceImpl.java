package com.rishiqing.qywx.web.service.website.impl;

import com.alibaba.fastjson.JSONObject;
import com.rishiqing.qywx.service.biz.rsq.RsqStaffService;
import com.rishiqing.qywx.service.common.isv.IsvManageService;
import com.rishiqing.qywx.service.model.corp.CorpStaffVO;
import com.rishiqing.qywx.service.model.isv.IsvVO;
import com.rishiqing.qywx.service.model.website.WebsiteLoginInfoVO;
import com.rishiqing.qywx.service.util.http.HttpUtilIsv;
import com.rishiqing.qywx.service.util.http.converter.Json2BeanConverter;
import com.rishiqing.qywx.web.service.website.WebsiteOauthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Wallace Mao
 * Date: 2018-05-10 20:23
 */
public class WebsiteOauthServiceImpl implements WebsiteOauthService {
    private static Logger logger = LoggerFactory.getLogger("CONSOLE_LOGGER");

    @Autowired
    private HttpUtilIsv httpUtilIsv;
    @Autowired
    private IsvManageService isvManageService;
    @Autowired
    private RsqStaffService rsqStaffService;

    @Override
    public CorpStaffVO registerLoginUser(String authCode, String corpId){
        //  根据authCode获取当前登录用户的信息
        IsvVO isv = isvManageService.getIsv(corpId);
        JSONObject jsonLogin = httpUtilIsv.getWebsiteLoginInfo(isv, authCode);
        logger.debug("====jsonLogin===={}", jsonLogin);
        WebsiteLoginInfoVO loginInfoVO = Json2BeanConverter.generateWebsiteLoginInfo(jsonLogin);

        logger.debug("====loginInfoVO===={}", loginInfoVO);
        //  将用户信息自动注册到日事清
        rsqStaffService.pushAndCreateStaff(null, null, loginInfoVO.getCorpStaffVO());

        return loginInfoVO.getCorpStaffVO();
    }
}
