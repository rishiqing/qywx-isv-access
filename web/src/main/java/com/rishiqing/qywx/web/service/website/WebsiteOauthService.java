package com.rishiqing.qywx.web.service.website;

import com.rishiqing.qywx.service.model.corp.CorpStaffVO;
import com.rishiqing.qywx.service.model.website.WebsiteLoginInfoVO;

/**
 * @author Wallace Mao
 * Date: 2018-05-10 18:59
 */
public interface WebsiteOauthService {
    CorpStaffVO registerLoginUser(String authCode, String appId);

    WebsiteLoginInfoVO getWebsiteLoginInfo(String authCode, String corpId);

    String generateState();

    Boolean checkState(String state);
}
