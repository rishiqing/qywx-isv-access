package com.rishiqing.qywx.service.util.http;

import com.alibaba.fastjson.JSONObject;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.rishiqing.qywx.service.constant.RequestUrl;
import com.rishiqing.common.exception.HttpException;
import com.rishiqing.qywx.service.model.corp.CorpTokenVO;
import com.rishiqing.common.util.http.client.RestHttpClient;
import com.rishiqing.qywx.service.model.isv.SuiteTokenVO;

import java.util.HashMap;
import java.util.Map;

public class HttpUtilAuth {
    private RestHttpClient restHttpClient;

    public HttpUtilAuth(RestHttpClient restHttpClient) {
        this.restHttpClient = restHttpClient;
    }

    /**
     * 参考https://work.weixin.qq.com/api/doc#10028/获取code
     * @param corpTokenVO
     * @param code
     * @return
     * @throws HttpException
     * @throws UnirestException
     */
    public JSONObject getLoginUser(CorpTokenVO corpTokenVO, String code) {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("corpId", corpTokenVO.getCorpId());
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("access_token", corpTokenVO.getCorpToken());
        queryMap.put("code", code);
        return restHttpClient.get(
                RequestUrl.AUTH_LOGIN_USER,
                queryMap,
                options
        );
    }

    /**
     * 获取预授权码
     * @param suiteTokenVO
     * @return
     */
    public JSONObject getPreAuthCode(SuiteTokenVO suiteTokenVO){
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("suite_access_token", suiteTokenVO.getSuiteToken());
        return restHttpClient.get(
                RequestUrl.GET_PRE_AUTH_CODE,
                queryMap,
                null
        );
    }

//    public JSONObject getDepartmentList(CorpTokenVO corpTokenVO, CorpDeptVO corpDeptVO) throws UnirestException, HttpException {
//        Map<String, String> options = new HashMap<>();
//        options.put("corpId", corpTokenVO.getCorpId());
//        Map<String, Object> queryMap = new HashMap<>();
//        queryMap.put("access_token", corpTokenVO.getCorpToken());
//        if(corpDeptVO != null){
//            queryMap.put("id", corpDeptVO.getDeptId());
//        }
//        return requestClient.get(
//                RequestUrl.DEPARTMENT_LIST,
//                queryMap,
//                options
//        );
//    }
//
//    public JSONObject getDepartmentStaffList(CorpTokenVO corpTokenVO, @Nullable CorpDeptVO corpDeptVO) throws HttpException, UnirestException {
//        Map<String, String> options = new HashMap<>();
//        options.put("corpId", corpTokenVO.getCorpId());
//        Map<String, Object> queryMap = new HashMap<>();
//        queryMap.put("access_token", corpTokenVO.getCorpToken());
//        queryMap.put("fetch_child", "1");  //递归获取
//        Long deptId = 1L;
//        if(corpDeptVO != null){
//            deptId = corpDeptVO.getDeptId();
//        }
//        queryMap.put("department_id", deptId);
//
//        return requestClient.get(
//                RequestUrl.DEPARTMENT_STAFF_LIST,
//                queryMap,
//                options
//        );
//    }


    public RestHttpClient getRestHttpClient() {
        return restHttpClient;
    }

    public void setRestHttpClient(RestHttpClient restHttpClient) {
        this.restHttpClient = restHttpClient;
    }
}
