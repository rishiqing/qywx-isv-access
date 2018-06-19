package com.rishiqing.common.util.http.impl;

import com.alibaba.fastjson.JSONObject;
import com.rishiqing.common.util.http.client.RestHttpClient;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Wallace Mao
 * Date: 2018-06-19 17:58
 */
public class HttpUtilRsqAuthImpl {
    private static final String URL_TOKEN_LOGIN = "/task/qywxOauth/tokenLogin";

    public HttpUtilRsqAuthImpl(String rootDomain, RestHttpClient restHttpClient) {
        this.rootDomain = rootDomain;
        this.restHttpClient = restHttpClient;
    }

    private String rootDomain;
    private RestHttpClient restHttpClient;

    public String getRootDomain() {
        return rootDomain;
    }

    public void setRootDomain(String rootDomain) {
        this.rootDomain = rootDomain;
    }

    public RestHttpClient getRestHttpClient() {
        return restHttpClient;
    }

    public void setRestHttpClient(RestHttpClient restHttpClient) {
        this.restHttpClient = restHttpClient;
    }

    public String tokenLogin(String token){
        String url = this.rootDomain + URL_TOKEN_LOGIN ;
        Map queryMap = new HashMap<String, String>();
        queryMap.put("token", token);

        JSONObject jsonObject = restHttpClient.get(url, queryMap, null);

        return "";
    }
}
