package com.rishiqing.common.util.http.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rishiqing.common.exception.HttpException;
import com.rishiqing.common.exception.RsqSyncException;
import com.rishiqing.common.model.RsqCommonUserVO;
import com.rishiqing.common.model.RsqDepartmentVO;
import com.rishiqing.common.model.RsqTeamVO;
import com.rishiqing.common.util.http.HttpUtilRsqSync;
import com.rishiqing.common.util.http.client.RestHttpClient;
import com.rishiqing.common.util.http.converter.RsqRequestConverter;
import com.rishiqing.common.util.http.converter.RsqResponseConverter;
import com.rishiqing.qywx.dao.model.order.OrderRsqPushEventDO;
import com.rishiqing.qywx.service.util.rsq.UserGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Wallace Mao
 * Date: 2018-02-08 14:40
 */
public class HttpUtilRsqSyncImpl implements HttpUtilRsqSync {
    private static final String URL_CREATE_TEAM = "/task/v2/tokenAuth/autoCreate/createTeam";
    private static final String URL_CREATE_DEPARTMENT = "/task/v2/tokenAuth/autoCreate/createDepartment";
    private static final String URL_UPDATE_DEPARTMENT = "/task/v2/tokenAuth/autoCreate/updateDepartment";
    private static final String URL_DELETE_DEPARTMENT = "/task/v2/tokenAuth/autoCreate/deleteDepartment";
    private static final String URL_CREATE_USER = "/task/v2/tokenAuth/autoCreate/createUser";
    private static final String URL_UPDATE_USER = "/task/v2/tokenAuth/autoCreate/updateUser";
    private static final String URL_USER_LEAVE_TEAM = "/task/v2/tokenAuth/autoCreate/userLeaveTeam";
    private static final String URL_SET_USER_ADMIN = "/task/v2/tokenAuth/autoCreate/userSetAdmin";
    private static final String URL_CHARGE = "/task/v2/tokenAuth/pay/recharge";

    public HttpUtilRsqSyncImpl(String rootDomain, RestHttpClient restHttpClient) {
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

    @Override
    public RsqTeamVO createCorp(String appName, String appToken, RsqTeamVO rsqTeamVO){
        String url = this.rootDomain + URL_CREATE_TEAM ;
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("token", appToken);
        JSONObject jsonReq = RsqRequestConverter.RsqTeamVO2Json(rsqTeamVO);

        JSONObject jsonObject = restHttpClient.post(url, queryMap, null, JSONObject.toJSONString(jsonReq), null);
        checkResponse(jsonObject);

        return RsqResponseConverter.Json2RsqTeamVO(jsonObject);
    }

    @Override
    public RsqDepartmentVO createDepartment(String appName, String appToken, RsqTeamVO rsqTeamVO, RsqDepartmentVO rsqDepartmentVO){
        String url = this.rootDomain + URL_CREATE_DEPARTMENT;
        Map queryMap = new HashMap<String, String>();
        queryMap.put("token", appToken);
        JSONObject params = new JSONObject();
        params.put("name", rsqDepartmentVO.getName());
        params.put("teamId", rsqTeamVO.getId());
        params.put("orderNum", rsqDepartmentVO.getOrderNum());

        params.put("outerId", rsqDepartmentVO.getCorpId() + "--" + rsqDepartmentVO.getDeptId());

        String parentId = "0";
        if(null != rsqDepartmentVO.getParentId()){
            parentId = rsqDepartmentVO.getParentId();
        }
        params.put("parentId", parentId);

        JSONObject jsonObject =  restHttpClient.post(url, queryMap, null, JSONObject.toJSONString(params), null);
        checkResponse(jsonObject);

        return RsqResponseConverter.Json2RsqDepartmentVO(jsonObject);
    }

    @Override
    public RsqDepartmentVO updateDepartment(String appName, String appToken, RsqTeamVO rsqTeamVO, RsqDepartmentVO rsqDepartmentVO){
        String url = this.rootDomain + URL_UPDATE_DEPARTMENT;
        Map queryMap = new HashMap<String, String>();
        queryMap.put("token", appToken);
        JSONObject params = new JSONObject();
        params.put("id", rsqDepartmentVO.getId());
        //  only update changed properties
        if(null != rsqDepartmentVO.getName()){
            params.put("name", rsqDepartmentVO.getName());
        }
        if(null != rsqDepartmentVO.getParentId()){
            params.put("parentId", rsqDepartmentVO.getParentId());
        }
        if(null != rsqDepartmentVO.getOrderNum()){
            params.put("orderNum", rsqDepartmentVO.getOrderNum());
        }

        JSONObject jsonObject = restHttpClient.post(url, queryMap, null, JSONObject.toJSONString(params), null);
        checkResponse(jsonObject);

        return RsqResponseConverter.Json2RsqDepartmentVO(jsonObject);
    }

    @Override
    public RsqDepartmentVO deleteDepartment(String appName, String appToken, RsqTeamVO rsqTeamVO, RsqDepartmentVO rsqDepartmentVO){
        String url = this.rootDomain + URL_DELETE_DEPARTMENT;
        Map queryMap = new HashMap<String, String>();
        queryMap.put("token", appToken);
        JSONObject params = new JSONObject();
        //  only need id property
        params.put("id", rsqDepartmentVO.getId());

        JSONObject jsonObject = restHttpClient.post(url, queryMap, null, JSONObject.toJSONString(params), null);
        checkResponse(jsonObject);

        return RsqResponseConverter.Json2RsqDepartmentVO(jsonObject);
    }

    @Override
    public RsqCommonUserVO createUser(String appName, String appToken, RsqTeamVO rsqTeamVO, RsqCommonUserVO rsqCommonUserVO){
        String url = this.rootDomain + URL_CREATE_USER;
        Map queryMap = new HashMap<String, String>();
        queryMap.put("token", appToken);
        JSONObject params = new JSONObject();
        params.put("username", rsqCommonUserVO.getUsername());
        params.put("password", rsqCommonUserVO.getPassword());
        params.put("realName", rsqCommonUserVO.getRealName());
        params.put("outerId", UserGenerator.generateUserOuterId(rsqCommonUserVO.getCorpId(), rsqCommonUserVO.getUserId()));
        params.put("unionId", rsqCommonUserVO.getUnionId());
        if(null != rsqTeamVO){
            params.put("teamId", rsqTeamVO.getId());
        }else{
            params.put("teamId", null);
        }

        if(null != rsqCommonUserVO.getDepartment()){
            params.put("department", JSONArray.parse(rsqCommonUserVO.getDepartment()));
        }

        JSONObject jsonObject = restHttpClient.post(url, queryMap, null, JSONObject.toJSONString(params), null);
        checkResponse(jsonObject);

        return RsqResponseConverter.Json2RsqCommonUserVO(jsonObject);
    }

    @Override
    public RsqCommonUserVO updateUser(String appName, String appToken, RsqTeamVO rsqTeamVO, RsqCommonUserVO rsqCommonUserVO){
        String url = this.rootDomain + URL_UPDATE_USER;
        Map queryMap = new HashMap<String, String>();
        queryMap.put("token", appToken);
        JSONObject params = new JSONObject();
        params.put("id", rsqCommonUserVO.getId());
        params.put("realName", rsqCommonUserVO.getRealName());
        params.put("unionId", rsqCommonUserVO.getUnionId());

        if(null != rsqCommonUserVO.getDepartment()){
            params.put("department", JSONArray.parse(rsqCommonUserVO.getDepartment()));
        }

        JSONObject jsonObject = restHttpClient.post(url, queryMap, null, JSONObject.toJSONString(params), null);
        checkResponse(jsonObject);

        return RsqResponseConverter.Json2RsqCommonUserVO(jsonObject);
    }

    @Override
    public RsqCommonUserVO userLeaveTeam(String appName, String appToken, RsqTeamVO rsqTeamVO, RsqCommonUserVO rsqCommonUserVO){
        String url = this.rootDomain + URL_USER_LEAVE_TEAM;
        Map queryMap = new HashMap<String, String>();
        queryMap.put("token", appToken);
        JSONObject params = new JSONObject();
        params.put("id", rsqCommonUserVO.getId());

        JSONObject jsonObject = restHttpClient.post(url, queryMap, null, JSONObject.toJSONString(params), null);
        checkResponse(jsonObject);

        return RsqResponseConverter.Json2RsqCommonUserVO(jsonObject);
    }

    @Override
    public RsqCommonUserVO setUserAdmin(String appName, String appToken, RsqTeamVO rsqTeamVO, RsqCommonUserVO rsqCommonUserVO){
        String url = this.rootDomain + URL_SET_USER_ADMIN;
        Map queryMap = new HashMap<String, String>();
        queryMap.put("token", appToken);
        JSONObject params = new JSONObject();
        params.put("id", rsqCommonUserVO.getId());
        params.put("isAdmin", rsqCommonUserVO.getAdmin());

        JSONObject jsonObject = restHttpClient.post(url, queryMap, null, JSONObject.toJSONString(params), null);
        checkResponse(jsonObject);

        return RsqResponseConverter.Json2RsqCommonUserVO(jsonObject);
    }

    @Override
    public void doCharge(String appToken, String rsqProductName, OrderRsqPushEventDO pushEventVO){
        String url = this.rootDomain + URL_CHARGE ;
        // 转成yyyy-MM-dd HH:mm:ss的格式
        String encodedDateString = parseFormat(pushEventVO.getServiceStopTime());
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("token", appToken);
        queryMap.put("productName", rsqProductName);
        queryMap.put("userLimit", checkMax(pushEventVO.getQuantity()));
        queryMap.put("deadLine", encodedDateString);
        queryMap.put("teamId", pushEventVO.getRsqTeamId());

        restHttpClient.post(url, queryMap, null, null, null);
    }

    private String parseFormat(Long mills) {
        Date date = new Date(mills);
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dt.format(date);
    }

    private Long checkMax(Long value) {
        return value > Integer.MAX_VALUE ? Integer.MAX_VALUE : value;
    }

    private void checkResponse(JSONObject jsonObject){
        if (jsonObject.containsKey("errcode") && !jsonObject.getLong("errcode").equals(0L)) {
            throw new HttpException("rsq request error: errcode is " + jsonObject.get("errcode") + " and errmsg is " + jsonObject.get("errmsg"));
        }
    }
}
