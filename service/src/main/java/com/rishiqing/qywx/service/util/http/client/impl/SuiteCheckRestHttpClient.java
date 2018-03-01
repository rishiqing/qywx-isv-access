package com.rishiqing.qywx.service.util.http.client.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.rishiqing.qywx.service.common.corp.CorpSuiteManageService;
import com.rishiqing.qywx.service.common.corp.CorpTokenManageService;
import com.rishiqing.qywx.service.common.isv.SuiteManageService;
import com.rishiqing.qywx.service.common.isv.SuiteTicketManageService;
import com.rishiqing.qywx.service.common.isv.SuiteTokenManageService;
import com.rishiqing.qywx.service.constant.RequestUrl;
import com.rishiqing.common.exception.HttpException;
import com.rishiqing.qywx.service.model.corp.CorpSuiteVO;
import com.rishiqing.qywx.service.model.corp.CorpTokenVO;
import com.rishiqing.qywx.service.model.isv.SuiteTicketVO;
import com.rishiqing.qywx.service.model.isv.SuiteTokenVO;
import com.rishiqing.qywx.service.model.isv.SuiteVO;
import com.rishiqing.common.util.http.client.RestHttpClient;
import com.rishiqing.qywx.service.util.http.converter.Bean2JsonConverter;
import com.rishiqing.qywx.service.util.http.converter.Json2BeanConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @author Wallace Mao
 * 重新实现的RestHttpClient，可以自动判断suite和corp的token是否失效，如果失效，那么会自动获取
 */
public class SuiteCheckRestHttpClient implements RestHttpClient {
    private static final Logger logger = LoggerFactory.getLogger("SERVICE_HTTP_REQUEST_LOGGER");
    @Autowired
    private Map isvGlobal;
    @Autowired
    private SuiteManageService suiteManageService;
    @Autowired
    private SuiteTicketManageService suiteTicketManageService;
    @Autowired
    private SuiteTokenManageService suiteTokenManageService;
    @Autowired
    private CorpSuiteManageService corpSuiteManageService;
    @Autowired
    private CorpTokenManageService corpTokenManageService;

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
     * 1  如果结果是suiteAccessToken失效，那么先请求并保存suiteAccessToken， 然后在执行post
     * 2  如果结果是corpAccessToken失效，那么先请求并保存corpAccessToken，然后再执行post
     * 顶层不需要关心细节
     * @return
     * @throws HttpException
     */
    @Override
    public JSONObject post(String path, Map<String, Object> queryMap, Map<String, Object> fieldMap, String body, Map<String, Object> options) {
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
            throw new HttpException("http post request getSuiteToken status error: " + resp.getStatus() + ", " + resp.getBody());
        }
        JSONObject jsonResponse = JSON.parseObject(resp.getBody());
        logger.info("{} post response: {}", path, jsonResponse);
        Long errcode = jsonResponse.getLong("errcode");
        if(errcode != null && !errcode.equals(0L)){
            if(errcode.equals(42009L) || errcode.equals(40082L)){
                //  如果遇到suiteAccessToken失效，重新获取suiteAccessToken
                logger.warn("RestHttpClient post suiteAccessToken expired, trying to fetch again, errcode: {}", errcode);
                String suiteToken = this.fetchAndSaveSuiteAccessToken();
                queryMap.put("suite_access_token", suiteToken);
                return this.post(path, queryMap, fieldMap, body, options);
            }else if(errcode.equals(42001L) || errcode.equals(40014L)){
                //  如果遇到corpAccessToken失效，重新获取corpAccessToken
                logger.warn("RestHttpClient post corpAccessToken expired, trying to fetch again, errcode: {}", errcode);
                String corpToken = this.fetchAndSaveCorpAccessToken((String)options.get("corpId"));
                queryMap.put("access_token", corpToken);
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
        logger.info("{} post response: {}", path, jsonResponse);
        Long errcode = jsonResponse.getLong("errcode");
        if(errcode != null && !errcode.equals(0L)){
            if(errcode.equals(42009L) || errcode.equals(40082L)){
                //  如果遇到suiteAccessToken失效，重新获取suiteAccessToken
                logger.warn("RestHttpClient get suiteAccessToken expired, trying to fetch again, errcode: {}", errcode);
                String suiteToken = this.fetchAndSaveSuiteAccessToken();
                queryMap.put("suite_access_token", suiteToken);
                return this.get(path, queryMap, options);
            }else if(errcode.equals(42001L) || errcode.equals(40014L)){
                //  如果遇到corpAccessToken失效，重新获取corpAccessToken
                logger.warn("RestHttpClient get corpAccessToken expired, trying to fetch again, errcode: {}", errcode);
                String corpToken = this.fetchAndSaveCorpAccessToken((String)options.get("corpId"));
                queryMap.put("access_token", corpToken);
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
    private String fetchAndSaveSuiteAccessToken() {
        String suiteKey = (String)isvGlobal.get("suiteKey");
        SuiteVO suite = suiteManageService.getSuiteInfoByKey(suiteKey);
        SuiteTicketVO ticket = suiteTicketManageService.getSuiteTicket(suiteKey);
        JSONObject params = Bean2JsonConverter.prepareSuiteTicket(suite, ticket);
        HttpResponse<String> resp =
                null;
        try {
            resp = Unirest.post(RequestUrl.SUITE_ACCESS_TOKEN)
                    .body(params.toString())
                    .asString();
        } catch (UnirestException e) {
            throw new HttpException("http request exception: ", e);
        }
        if(resp.getStatus() > 200){
            throw new HttpException("http request getSuiteToken status error: " + resp.getStatus() + ", " + resp.getBody());
        }
        JSONObject jsonResponse = JSON.parseObject(resp.getBody());
        Long errcode = jsonResponse.getLong("errcode");
        logger.info("{} post response: {}", RequestUrl.SUITE_ACCESS_TOKEN, jsonResponse);
        if(errcode != null && !errcode.equals(0L)){
            throw new HttpException(errcode, jsonResponse.getString("errmsg"));
        }
        SuiteTokenVO suiteTokenVO = Json2BeanConverter.generateSuiteToken(suiteKey, jsonResponse);
        suiteTokenManageService.saveSuiteToken(suiteTokenVO);
        return suiteTokenVO.getSuiteToken();
    }

    /**
     * 获取并保存corpAccessToken，如果suiteAccessToken失效，那么会先获取suiteAccessToken
     * 私密方法，仅供RequestClient类内使用
     * @param corpId
     * @throws HttpException
     */
    private String fetchAndSaveCorpAccessToken(String corpId) {
        String suiteKey = (String)isvGlobal.get("suiteKey");
        CorpSuiteVO corpSuiteDO = corpSuiteManageService.getCorpSuite(suiteKey, corpId);
        SuiteTokenVO suiteTokenDO = suiteTokenManageService.getSuiteToken(suiteKey);
        JSONObject params = Bean2JsonConverter.preparePermanentCode(suiteTokenDO, corpSuiteDO);
        HttpResponse<String> resp =
                null;
        try {
            resp = Unirest.post(RequestUrl.CORP_ACCESS_TOKEN)
                    .queryString("suite_access_token", suiteTokenDO.getSuiteToken())
                    .body(params.toString())
                    .asString();
        } catch (UnirestException e) {
            throw new HttpException("http request exception: ", e);
        }
        if(resp.getStatus() > 200){
            throw new HttpException("http request getCorpAccessToken status error: " + resp.getStatus() + ", " + resp.getBody());
        }
        JSONObject jsonResponse = JSON.parseObject(resp.getBody());
        Long errcode = jsonResponse.getLong("errcode");
        logger.info("{} post response: {}", RequestUrl.CORP_ACCESS_TOKEN, jsonResponse);
        if(errcode != null && !errcode.equals(0L)){
            //  如果遇到suiteAccessToken失效，那么重新获取suiteAccessToken
            if(errcode.equals(42009L) || errcode.equals(40082L)){
                this.fetchAndSaveSuiteAccessToken();
                return this.fetchAndSaveCorpAccessToken(corpId);
            }else{
                throw new HttpException(errcode, jsonResponse.getString("errmsg"));
            }
        }else{
            CorpTokenVO corpTokenVO = Json2BeanConverter.generateCorpToken(suiteKey, corpId, jsonResponse);
            corpTokenManageService.saveCorpToken(corpTokenVO);
            return corpTokenVO.getCorpToken();
        }
    }
}
