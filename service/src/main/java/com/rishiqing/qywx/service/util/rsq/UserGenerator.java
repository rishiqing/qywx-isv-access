package com.rishiqing.qywx.service.util.rsq;

import org.apache.commons.lang.RandomStringUtils;

import java.util.Date;

/**
 * @author Wallace Mao
 * Date: 2018-06-21 19:43
 */
public class UserGenerator {
    public static String generateRsqUsername(String appName){
        StringBuffer sb = new StringBuffer();
        sb.append(RandomStringUtils.randomAlphabetic(5))
                .append("_")
                .append(new Date().getTime())
                .append("@")
                .append(appName)
                .append(".rishiqing.com");
        return  sb.toString();
    }

    public static String generateRsqPassword(String appName){
        return RandomStringUtils.randomAlphabetic(6);
    }

    public static String generateUserOuterId(String corpId, String userId){
        return corpId + "--" + userId;
    }
}
