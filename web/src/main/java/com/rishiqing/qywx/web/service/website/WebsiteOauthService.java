package com.rishiqing.qywx.web.service.website;

import com.rishiqing.qywx.service.model.corp.CorpStaffVO;

/**
 * @author Wallace Mao
 * Date: 2018-05-10 18:59
 */
public interface WebsiteOauthService {
    CorpStaffVO registerLoginUser(String authCode, String appId);

    String generateState();

    Boolean checkState(String state);
}
