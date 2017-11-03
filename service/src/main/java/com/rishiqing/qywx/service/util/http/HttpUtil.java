package com.rishiqing.qywx.service.util.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.rishiqing.qywx.service.constant.RequestUrl;
import com.rishiqing.qywx.service.exception.HttpException;
import com.rishiqing.qywx.service.exception.SuiteAccessTokenExpiredException;
import com.rishiqing.qywx.service.model.corp.CorpSuiteVO;
import com.rishiqing.qywx.service.model.isv.SuiteTicketVO;
import com.rishiqing.qywx.service.model.isv.SuiteTokenVO;
import com.rishiqing.qywx.service.model.isv.SuiteVO;
import com.rishiqing.qywx.service.util.http.converter.Bean2JsonConverter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class HttpUtil {
    private RequestClient requestClient;

    public HttpUtil(RequestClient requestClient) {
        this.requestClient = requestClient;
    }

    public JSONObject getSuiteToken(SuiteVO suiteVO, SuiteTicketVO suiteTicketVO) throws UnirestException, HttpException {
        JSONObject params = Bean2JsonConverter.prepareSuiteTicket(suiteVO, suiteTicketVO);
        return requestClient.post(
                RequestUrl.SUITE_ACCESS_TOKEN, null, null, params.toString(), null
        );
    }

    public JSONObject getPermanentCode(SuiteTokenVO suiteTokenVO, String authCode) throws UnirestException, HttpException {
        JSONObject params = Bean2JsonConverter.prepareAuthCode(suiteTokenVO, authCode);
        Map<String ,String> options = new HashMap<>();
        options.put("suiteKey", suiteTokenVO.getSuiteKey());
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("suite_access_token", suiteTokenVO.getSuiteToken());
        return requestClient.post(
                RequestUrl.PERMANENT_CODE, queryMap, null, params.toString(), options
        );
    }

    public JSONObject getCorpAuthInfo(SuiteTokenVO suiteTokenVO, CorpSuiteVO corpSuiteVO) throws UnirestException, HttpException {
        JSONObject params = Bean2JsonConverter.preparePermanentCode(suiteTokenVO, corpSuiteVO);
        Map<String ,String> options = new HashMap<>();
        options.put("suiteKey", suiteTokenVO.getSuiteKey());
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("suite_access_token", suiteTokenVO.getSuiteToken());
        return requestClient.post(
                RequestUrl.CORP_AUTH_INFO, queryMap, null, params.toString(), options
        );
    }

    public JSONObject getCorpAccessToken(SuiteTokenVO suiteTokenVO, CorpSuiteVO corpSuiteVO) throws UnirestException, HttpException {
        JSONObject params = Bean2JsonConverter.preparePermanentCode(suiteTokenVO, corpSuiteVO);
        Map<String ,String> options = new HashMap<>();
        options.put("suiteKey", suiteTokenVO.getSuiteKey());
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("suite_access_token", suiteTokenVO.getSuiteToken());
        return requestClient.post(
                RequestUrl.CORP_ACCESS_TOKEN, queryMap, null, params.toString(), options
        );
    }

    public RequestClient getRequestClient() {
        return requestClient;
    }

    public void setRequestClient(RequestClient requestClient) {
        this.requestClient = requestClient;
    }
}
