package com.rishiqing.qywx.service.util.http;

import com.alibaba.fastjson.JSONObject;
import com.rishiqing.common.util.http.client.RestHttpClient;
import com.rishiqing.qywx.service.constant.RequestUrl;
import com.rishiqing.qywx.service.model.isv.IsvVO;
import com.rishiqing.qywx.service.model.website.RegisterInfoVO;
import com.rishiqing.qywx.service.util.http.converter.Bean2JsonConverter;

import java.util.HashMap;
import java.util.Map;

public class HttpUtilIsv {
    private RestHttpClient restHttpClient;

    public HttpUtilIsv(RestHttpClient restHttpClient) {
        this.restHttpClient = restHttpClient;
    }

    public JSONObject getRegisterCode(IsvVO isvVO, RegisterInfoVO registerInfoVO) {
        JSONObject params = Bean2JsonConverter.prepareGetRegisterCode(registerInfoVO);
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("provider_access_token", isvVO.getProviderAccessToken());

        return restHttpClient.post(
                RequestUrl.WEBSITE_REGISTER_CODE,
                queryMap,
                null,
                JSONObject.toJSONString(params),
                null
        );
    }

    public JSONObject getWebsiteLoginInfo(IsvVO isvVO, String authCode){
        JSONObject params = Bean2JsonConverter.prepareGetWebsiteLoginInfo(authCode);
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("provider_access_token", isvVO.getProviderAccessToken());

        return restHttpClient.post(
                RequestUrl.WEBSITE_LOGIN_INFO,
                queryMap,
                null,
                JSONObject.toJSONString(params),
                null
        );
    }

    public RestHttpClient getRestHttpClient() {
        return restHttpClient;
    }

    public void setRestHttpClient(RestHttpClient restHttpClient) {
        this.restHttpClient = restHttpClient;
    }
}
