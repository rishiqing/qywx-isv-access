package com.rishiqing.qywx.web.service.website;

/**
 * @author Wallace Mao
 * Date: 2018-05-10 18:59
 */
public interface WebsiteOauthService {
    void registerLoginUser(String authCode, String appId);
}
