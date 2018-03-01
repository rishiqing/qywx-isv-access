package com.rishiqing.qywx.web.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.rishiqing.qywx.service.common.corp.CorpJsapiTicketManageService;
import com.rishiqing.qywx.service.common.corp.CorpTokenManageService;
import com.rishiqing.common.exception.HttpException;
import com.rishiqing.qywx.service.model.corp.CorpJsapiTicketVO;
import com.rishiqing.qywx.service.model.corp.CorpTokenVO;
import com.rishiqing.qywx.service.util.http.HttpUtilCorp;
import com.rishiqing.qywx.service.util.http.converter.Json2BeanConverter;
import com.rishiqing.qywx.web.exception.JsConfigException;
import com.rishiqing.qywx.web.service.JsConfigService;
import com.rishiqing.qywx.web.util.codec.JsapiSignature;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class JsConfigServiceImpl implements JsConfigService {
    @Autowired
    private Map isvGlobal;
    @Autowired
    private CorpTokenManageService corpTokenManageService;
    @Autowired
    private CorpJsapiTicketManageService corpJsapiTicketManageService;
    @Autowired
    private HttpUtilCorp httpUtilCorp;
    @Override
    public Map<String, Object> getJsapiSignature(String url, String corpId) {
        String suiteKey = (String)isvGlobal.get("suiteKey");
        CorpJsapiTicketVO jsTicket = corpJsapiTicketManageService.getCorpJsapiTicket(suiteKey, corpId);
        String sig = "";
        String nonceStr = getRandomStr(16);
        Long timestamp = new Date().getTime() / 1000;  //取秒数
        try {
            //  如果是首次加载，jsTicket为null，则先执行refreshJsapiTicket
            if(jsTicket == null){
                this.refreshJsapiTicket(corpId);
                jsTicket = corpJsapiTicketManageService.getCorpJsapiTicket(suiteKey, corpId);
            }
            sig = JsapiSignature.getSignature(url, nonceStr, timestamp, jsTicket.getCorpJsapiTicket());
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new JsConfigException("NoSuchAlgorithmException or UnsupportedEncodingException internal error: ", e);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("signature", sig);
        result.put("nonceStr", nonceStr);
        result.put("timeStamp", timestamp);
        result.put("appId", corpId);
        return result;
    }

    @Override
    public void refreshJsapiTicket(String corpId) {
        String suiteKey = (String)isvGlobal.get("suiteKey");
        CorpTokenVO corpTokenVO = corpTokenManageService.getCorpToken(suiteKey, corpId);
        JSONObject json = null;
        try {
            json = httpUtilCorp.getJsapiTicket(corpTokenVO);
        } catch (HttpException e) {
            throw new JsConfigException("js config exception", e);
        }
        CorpJsapiTicketVO jsapiTicketVO = Json2BeanConverter.generateCorpJsapiTicket(suiteKey, corpId, json);
        corpJsapiTicketManageService.saveCorpJsapiTicket(jsapiTicketVO);
    }

    // 随机生成16位字符串
    private String getRandomStr(int size) {
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < size; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}
