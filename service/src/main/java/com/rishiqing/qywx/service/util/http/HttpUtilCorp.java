package com.rishiqing.qywx.service.util.http;

import com.alibaba.fastjson.JSONObject;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.rishiqing.qywx.service.constant.RequestUrl;
import com.rishiqing.common.exception.HttpException;
import com.rishiqing.qywx.service.model.corp.CorpAppVO;
import com.rishiqing.qywx.service.model.corp.CorpDeptVO;
import com.rishiqing.qywx.service.model.corp.CorpSuiteVO;
import com.rishiqing.qywx.service.model.corp.CorpTokenVO;
import com.rishiqing.common.util.http.client.RestHttpClient;
import com.rishiqing.qywx.service.model.isv.SuiteTokenVO;
import com.rishiqing.qywx.service.util.http.converter.Bean2JsonConverter;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class HttpUtilCorp {
    private RestHttpClient restHttpClient;

    public HttpUtilCorp(RestHttpClient restHttpClient) {
        this.restHttpClient = restHttpClient;
    }

    public JSONObject getJsapiTicket(CorpTokenVO corpTokenVO) {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("corpId", corpTokenVO.getCorpId());
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("access_token", corpTokenVO.getCorpToken());
        return restHttpClient.get(
                RequestUrl.CORP_JSAPI_TICKET,
                queryMap,
                options
        );
    }

    public JSONObject getDepartmentList(CorpTokenVO corpTokenVO, CorpDeptVO corpDeptVO) {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("corpId", corpTokenVO.getCorpId());
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("access_token", corpTokenVO.getCorpToken());
        if(corpDeptVO != null){
            queryMap.put("id", corpDeptVO.getDeptId());
        }
        return restHttpClient.get(
                RequestUrl.DEPARTMENT_LIST,
                queryMap,
                options
        );
    }

    public JSONObject getDepartmentStaffList(CorpTokenVO corpTokenVO, @Nullable CorpDeptVO corpDeptVO, Boolean fetchChild) {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("corpId", corpTokenVO.getCorpId());
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("access_token", corpTokenVO.getCorpToken());
//        默认不使用递归调用
//        queryMap.put("fetch_child", "1");  //递归获取
        if(null != fetchChild && fetchChild){
            queryMap.put("fetch_child", "1");
        }else{
            queryMap.put("fetch_child", "0");
        }
        Long deptId = 1L;
        if(corpDeptVO != null){
            deptId = corpDeptVO.getDeptId();
        }
        queryMap.put("department_id", deptId);

        return restHttpClient.get(
                RequestUrl.DEPARTMENT_STAFF_LIST,
                queryMap,
                options
        );
    }

    public JSONObject getAppAdminList(SuiteTokenVO suiteTokenVO, CorpAppVO corpAppVO) {
        JSONObject params = Bean2JsonConverter.prepareGetAdminList(suiteTokenVO, corpAppVO);
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("suite_access_token", suiteTokenVO.getSuiteToken());

        return restHttpClient.post(
                RequestUrl.GET_APP_ADMIN_LIST,
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
