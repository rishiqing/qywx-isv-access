package com.rishiqing.qywx.service.util.http.client.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.rishiqing.common.exception.HttpException;
import com.rishiqing.common.util.http.client.RestHttpClient;
import com.rishiqing.qywx.service.common.corp.CorpSuiteManageService;
import com.rishiqing.qywx.service.common.corp.CorpTokenManageService;
import com.rishiqing.qywx.service.common.isv.*;
import com.rishiqing.qywx.service.constant.RequestUrl;
import com.rishiqing.qywx.service.model.corp.CorpSuiteVO;
import com.rishiqing.qywx.service.model.corp.CorpTokenVO;
import com.rishiqing.qywx.service.model.isv.IsvVO;
import com.rishiqing.qywx.service.model.isv.SuiteTicketVO;
import com.rishiqing.qywx.service.model.isv.SuiteTokenVO;
import com.rishiqing.qywx.service.model.isv.SuiteVO;
import com.rishiqing.qywx.service.util.http.converter.Bean2JsonConverter;
import com.rishiqing.qywx.service.util.http.converter.Json2BeanConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @author Wallace Mao
 * 重新实现的RestHttpClient，可以自动判断provider token是否失效，如果失效，那么会自动获取
 */
public class ProviderCheckRestHttpClient implements RestHttpClient {
    private static final Logger logger = LoggerFactory.getLogger("CONSOLE_LOGGER");
    @Autowired
    private GlobalSuite suite;
    @Autowired
    private IsvManageService isvManageService;

//    private String path;
//    private Map<String, Object> queryMap;
//    private Map<String, Object> fieldMap;
//    private String body;
//    private Map<String, String> options = new HashMap<>();  //需要用到的其他数据，可以包含suiteKey或者corpId等数据

//    public RestHttpClient(String path, Map<String, Object> queryMap, Map<String, Object> fieldMap, String body) {
//        this.path = path;
//        this.queryMap = queryMap;
//        this.fieldMap = fieldMap;
//        this.body = body;
//    }
//
//    public RestHttpClient(String path, Map<String, Object> queryMap, Map<String, Object> fieldMap, String body, Map<String, String> options) {
//        this.path = path;
//        this.queryMap = queryMap;
//        this.fieldMap = fieldMap;
//        this.body = body;
//        this.options = options;
//    }

    /**
     * post原理：
     * 1  如果结果是providerAccessToken失效，那么先请求并保存providerAccessToken， 然后在执行post
     * 顶层不需要关心细节
     * @return
     * @throws HttpException
     */
    @Override
    public JSONObject post(String path, Map<String, Object> queryMap, Map<String, Object> fieldMap, String body, Map<String, Object> options) {
        logger.debug("path: {}, queryMap: {}, fieldMap: {}, body: {}, options: {}", path, queryMap, fieldMap, body, options);
        HttpRequestWithBody request = Unirest.post(path);
        if(queryMap != null && !queryMap.isEmpty()){
            request.queryString(queryMap);
        }
        if(fieldMap != null && !fieldMap.isEmpty()){
            request.fields(fieldMap);
        }
        if(body != null){
            request.body(body);
        }
        HttpResponse<String> resp = null;
        try {
            resp = request.asString();
        } catch (UnirestException e) {
            throw new HttpException("http request exception: ", e);
        }
        if(resp.getStatus() > 200){
            throw new HttpException("http post request status error: " + resp.getStatus() + ", " + resp.getBody());
        }
        JSONObject jsonResponse = JSON.parseObject(resp.getBody());
        logger.debug("{} post response: {}", path, jsonResponse);
        Long errcode = jsonResponse.getLong("errcode");
        if(errcode != null && !errcode.equals(0L)){
            if(errcode.equals(42001L) || errcode.equals(40014L) || errcode.equals(40082L)){
                //  如果遇到providerAccessToken失效，重新获取providerAccessToken
                logger.warn("RestHttpClient post providerAccessToken expired, trying to fetch again, errcode: {}", errcode);
                String token = this.fetchAndSaveProviderAccessToken();
                queryMap.put("provider_access_token", token);
                return this.post(path, queryMap, fieldMap, body, options);
            }else{
                throw new HttpException(errcode, jsonResponse.getString("errmsg"));
            }
        }else{
            return jsonResponse;
        }
    }

    /**
     *
     * @param path
     * @param queryMap
     * @param options
     * @return
     * @throws HttpException
     */
    @Override
    public JSONObject get(String path, Map<String, Object> queryMap, Map<String, Object> options) {
        logger.debug("path: {}, queryMap: {}, options: {}", path, queryMap, options);
        GetRequest request = Unirest.get(path);
        if(queryMap != null && !queryMap.isEmpty()){
            request.queryString(queryMap);
        }
        HttpResponse<String> resp = null;
        try {
            resp = request.asString();
        } catch (UnirestException e) {
            throw new HttpException("http request exception: ", e);
        }
        if(resp.getStatus() > 200){
            throw new HttpException("http get request getSuiteToken status error: " + resp.getStatus() + ", " + resp.getBody());
        }
        JSONObject jsonResponse = JSON.parseObject(resp.getBody());
        logger.debug("{} post response: {}", path, jsonResponse);
        Long errcode = jsonResponse.getLong("errcode");
        if(errcode != null && !errcode.equals(0L)){
            if(errcode.equals(42001L) || errcode.equals(40014L) || errcode.equals(40082L)){
                //  如果遇到providerAccessToken失效，重新获取providerAccessToken
                logger.warn("RestHttpClient get providerAccessToken expired, trying to fetch again, errcode: {}", errcode);
                String suiteToken = this.fetchAndSaveProviderAccessToken();
                queryMap.put("provider_access_token", suiteToken);
                return this.get(path, queryMap, options);
            }else{
                throw new HttpException(errcode, jsonResponse.getString("errmsg"));
            }
        }else{
            return jsonResponse;
        }
    }

    /**
     * 获取并保存suiteAccessToken，私密方法，仅供RequestClient类内使用
     * @throws HttpException
     * @return sutieAccessToken
     */
    private String fetchAndSaveProviderAccessToken() {
        //  读取套件基本信息
        String corpId = suite.getCorpId();
        IsvVO isv = isvManageService.getIsv(corpId);

        JSONObject params = Bean2JsonConverter.prepareProviderAccessToken(isv);
        HttpResponse<String> resp =
                null;
        try {
            resp = Unirest.post(RequestUrl.PROVIDER_ACCESS_TOKEN)
                    .body(params.toString())
                    .asString();
        } catch (UnirestException e) {
            throw new HttpException("http request exception: ", e);
        }
        if(resp.getStatus() > 200){
            throw new HttpException("http request getProviderAccessToken status error: " + resp.getStatus() + ", " + resp.getBody());
        }
        JSONObject jsonResponse = JSON.parseObject(resp.getBody());
        Long errcode = jsonResponse.getLong("errcode");
        logger.debug("{} post response: {}", RequestUrl.PROVIDER_ACCESS_TOKEN, jsonResponse);
        if(errcode != null && !errcode.equals(0L)){
            throw new HttpException(errcode, jsonResponse.getString("errmsg"));
        }
        IsvVO result = Json2BeanConverter.generateProviderAccessToken(corpId, jsonResponse);
        isv.setProviderAccessToken(result.getProviderAccessToken());
        isv.setProviderTokenUpdateTime(result.getProviderTokenUpdateTime());
        logger.debug("isvVO: {}", isv);
        isvManageService.saveIsv(isv);
        return isv.getProviderAccessToken();
    }
}
