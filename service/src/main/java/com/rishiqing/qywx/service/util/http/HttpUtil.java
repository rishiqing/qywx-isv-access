package com.rishiqing.qywx.service.util.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.rishiqing.qywx.service.constant.RequestUrl;
import com.rishiqing.common.exception.HttpException;
import com.rishiqing.qywx.service.model.corp.CorpSuiteVO;
import com.rishiqing.qywx.service.model.isv.SuiteTicketVO;
import com.rishiqing.qywx.service.model.isv.SuiteTokenVO;
import com.rishiqing.qywx.service.model.isv.SuiteVO;
import com.rishiqing.common.util.http.client.RestHttpClient;
import com.rishiqing.qywx.service.util.http.converter.Bean2JsonConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class HttpUtil {
    private static final Logger logger = LoggerFactory.getLogger("CONSOLE_LOGGER");
    private RestHttpClient restHttpClient;

    public HttpUtil(RestHttpClient restHttpClient) {
        this.restHttpClient = restHttpClient;
    }

    public JSONObject getSuiteToken(SuiteVO suiteVO, SuiteTicketVO suiteTicketVO){
        JSONObject params = Bean2JsonConverter.prepareSuiteTicket(suiteVO, suiteTicketVO);
        return restHttpClient.post(
                RequestUrl.SUITE_ACCESS_TOKEN, null, null, params.toString(), null
        );
    }

    public JSONObject getPermanentCode(SuiteTokenVO suiteTokenVO, String authCode) {
        JSONObject params = Bean2JsonConverter.prepareAuthCode(suiteTokenVO, authCode);
        logger.debug("====getPermanentCode===={}", JSON.toJSONString(params));
        Map<String ,Object> options = new HashMap<String, Object>();
        options.put("suiteKey", suiteTokenVO.getSuiteKey());
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("suite_access_token", suiteTokenVO.getSuiteToken());
        return restHttpClient.post(
                RequestUrl.PERMANENT_CODE, queryMap, null, params.toString(), options
        );
    }

    public JSONObject getCorpAuthInfo(SuiteTokenVO suiteTokenVO, CorpSuiteVO corpSuiteVO){
        JSONObject params = Bean2JsonConverter.preparePermanentCode(suiteTokenVO, corpSuiteVO);
        Map<String ,Object> options = new HashMap<String ,Object>();
        options.put("suiteKey", suiteTokenVO.getSuiteKey());
        Map<String, Object> queryMap = new HashMap<String ,Object>();
        queryMap.put("suite_access_token", suiteTokenVO.getSuiteToken());
        return restHttpClient.post(
                RequestUrl.CORP_AUTH_INFO, queryMap, null, params.toString(), options
        );
    }

    public JSONObject getCorpAccessToken(SuiteTokenVO suiteTokenVO, CorpSuiteVO corpSuiteVO) {
        JSONObject params = Bean2JsonConverter.preparePermanentCode(suiteTokenVO, corpSuiteVO);
        Map<String ,Object> options = new HashMap<String ,Object>();
        options.put("suiteKey", suiteTokenVO.getSuiteKey());
        Map<String, Object> queryMap = new HashMap<String ,Object>();
        queryMap.put("suite_access_token", suiteTokenVO.getSuiteToken());
        return restHttpClient.post(
                RequestUrl.CORP_ACCESS_TOKEN, queryMap, null, params.toString(), options
        );
    }

    public JSONObject getOrder(SuiteTokenVO suiteTokenVO, String orderId) {
        JSONObject params = Bean2JsonConverter.prepareOrder(orderId);
        Map<String ,Object> options = new HashMap<String ,Object>();
        options.put("suiteKey", suiteTokenVO.getSuiteKey());
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("suite_access_token", suiteTokenVO.getSuiteToken());
        return restHttpClient.post(
                RequestUrl.ORDER, queryMap, null, params.toString(), options);
    }

    public RestHttpClient getRestHttpClient() {
        return restHttpClient;
    }

    public void setRestHttpClient(RestHttpClient restHttpClient) {
        this.restHttpClient = restHttpClient;
    }
}
