package com.rishiqing.qywx.web.controller.security;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rishiqing.qywx.service.common.corp.CorpAppManageService;
import com.rishiqing.qywx.service.model.corp.CorpAppVO;
import com.rishiqing.qywx.service.model.corp.LoginUserVO;
import com.rishiqing.qywx.web.exception.ForbiddenException;
import com.rishiqing.qywx.web.exception.InternalServerErrorException;
import com.rishiqing.qywx.web.service.OauthService;
import com.rishiqing.qywx.web.service.security.LoginCookieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Wallace Mao
 * Date: 2018-06-19 14:34
 */
@Controller
@RequestMapping("/cookie")
public class LoginCookieController {
    private static final Logger logger = LoggerFactory.getLogger("WEB_CALLBACK_LOGGER");

    @Autowired
    private LoginCookieService loginCookieService;
    @Autowired
    private OauthService oauthService;
    @Autowired
    private CorpAppManageService corpAppManageService;

    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    @ResponseBody
    public Map cookieLogin (
            HttpServletResponse response,
            @RequestBody String jsonBody
    ) {
        logger.info("--cookieLogin-- body: {}", jsonBody);
        try {
            JSONObject json = JSON.parseObject(jsonBody);
            Long type = json.getLong("type");
            String code = json.getString("code");
            String corpId = json.getString("corpid");
            String suiteId = json.getString("suiteid");
            Long timestamp = json.getLong("timestamp");
            String nonce = json.getString("nonce");
            String sign = json.getString("sign");

            //  验证来源是否是企业微信
            if(!loginCookieService.checkSignature(sign, type, code, corpId, timestamp, nonce)){
                throw new ForbiddenException();
            }

            Map<String, Object> map = new HashMap<String, Object>();

            LoginUserVO loginUserVO = oauthService.getCorpLoginUserByCode(corpId, code);
            CorpAppVO corpAppVO = corpAppManageService.getCorpAppBySuiteKeyAndCorpId(suiteId, corpId);

            //  设置企业微信server的cookie
            Cookie qywxCookie = oauthService.generateClientUserCookie(corpAppVO.getAgentId().toString(), corpId, loginUserVO);
//            response.addCookie(cookie);
            //  自动登录到日事清后台，并获取session id的cookie，添加到cookie中
            List<Cookie> cookieList = loginCookieService.tokenLoginToRsq(corpId, loginUserVO.getUserId());
//            for(Cookie c : cookieList){
//                response.addCookie(c);
//            }

            cookieList.add(qywxCookie);

            //  返回cookie
            String strCookie = makeCookieString(cookieList);
            logger.debug("response cookie is: {}", strCookie);

            map.put("cookie", strCookie);
            map.put("expires_in", 14400);

            return map;
        } catch (Exception e) {
            logger.error("internal error in cookie login: request body is " + jsonBody, e);
            throw new InternalServerErrorException();
        }
    }

    private String makeCookieString(List<Cookie> cookies){
        StringBuilder sb = new StringBuilder();
        int len = cookies.size();
        for(int i = 0; i < len; i++){
            Cookie c = cookies.get(i);
            sb.append(c.getName())
                    .append("=")
                    .append(c.getValue());
            if(i != len - 1){
                sb.append("; ");
            }
        }
        return sb.toString();
    }

}
