package com.rishiqing.qywx.web.service;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.rishiqing.qywx.service.exception.HttpException;
import com.rishiqing.qywx.service.model.corp.LoginUserVO;

public interface OauthService {

    /**
     * 根据code获取当前登录用户
     * 参见：https://work.weixin.qq.com/api/doc#10028/获取code
     * @param corpId
     * @param code
     * @return
     */
    LoginUserVO getLoginUserByCode(String corpId, String code) throws HttpException, UnirestException;
}
