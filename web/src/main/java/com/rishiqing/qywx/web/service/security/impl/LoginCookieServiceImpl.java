package com.rishiqing.qywx.web.service.security.impl;

import com.rishiqing.common.util.http.HttpUtilRsqAuth;
import com.rishiqing.qywx.service.common.isv.GlobalSuite;
import com.rishiqing.qywx.service.model.corp.CorpStaffVO;
import com.rishiqing.qywx.web.exception.MessageSignException;
import com.rishiqing.qywx.web.service.RsqLoginService;
import com.rishiqing.qywx.web.service.security.LoginCookieService;
import com.rishiqing.qywx.web.util.codec.AesException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.Cookie;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @author Wallace Mao
 * Date: 2018-06-19 15:08
 */
public class LoginCookieServiceImpl implements LoginCookieService {
    public static final Logger logger = LoggerFactory.getLogger("CONSOLE_LOGGER");

    @Autowired
    private GlobalSuite suite;
    @Autowired
    private HttpUtilRsqAuth httpUtilRsqAuth;
    @Autowired
    private RsqLoginService rsqLoginService;

    @Override
    public Boolean checkSignature(String signature, Long type, String code, String corpId, Long timestamp, String nonce){
        String suiteId = suite.getSuiteKey();
        String token = suite.getToken();

        List<String> list = new ArrayList<>();
        list.add(token);
        list.add(type.toString());
        list.add(code);
        list.add(corpId);
        list.add(suiteId);
        list.add(timestamp.toString());
        list.add(nonce);

        String orderedString = concatOrderedString(list);
        logger.debug("orderedString is: " + orderedString);

        String newSign = signSha1(orderedString);
        logger.debug("new Sign is: {}, old sign is: {}, should be EQUAL!", newSign, signature);

        return newSign.equals(signature);
    }

    @Override
    public List<Cookie> tokenLoginToRsq(String corpId, String userId){
        CorpStaffVO corpStaffVO = new CorpStaffVO();
        corpStaffVO.setCorpId(corpId);
        corpStaffVO.setUserId(userId);
        String token = rsqLoginService.generateLoginToken(corpStaffVO);
        logger.debug("tokenLoginToRsq token is: {}", token);
        Map<String, String> cookieMap = httpUtilRsqAuth.tokenLogin(token);
        List<Cookie> list = new ArrayList<>();

        for(Map.Entry<String, String> entry : cookieMap.entrySet()){
            list.add(new Cookie(entry.getKey(), entry.getValue()));
        }
        return list;
    }

    private String concatOrderedString(List<String> orgList){
        Collections.sort(orgList);
        StringBuilder sb = new StringBuilder();
        for(String str : orgList){
            sb.append(str);
        }
        return sb.toString();
    }

    private String signSha1(String str){
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-1");
            byte[] hashedBytes = digest.digest(str.getBytes("UTF-8"));

            StringBuilder hexString = new StringBuilder();
            for (byte hashedByte : hashedBytes) {
                String shaHex = Integer.toHexString(hashedByte & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new MessageSignException(e);
        }
    }
}
