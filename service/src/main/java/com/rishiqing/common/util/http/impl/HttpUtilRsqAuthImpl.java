package com.rishiqing.common.util.http.impl;

import com.alibaba.fastjson.JSONObject;
import com.rishiqing.common.exception.HttpException;
import com.rishiqing.common.util.http.HttpUtilRsqAuth;
import com.rishiqing.common.util.http.client.RestHttpClient;
import com.rishiqing.qywx.service.model.corp.CorpStaffVO;
import org.apache.http.HttpEntity;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Wallace Mao
 * Date: 2018-06-19 17:58
 */
public class HttpUtilRsqAuthImpl implements HttpUtilRsqAuth {
    private static final String URL_TOKEN_LOGIN = "/task/qywxOauth/tokenLogin";
    private static final String SESSION_ID_NAME = "JSESSIONID";

    public HttpUtilRsqAuthImpl(String rootDomain) {
        this.rootDomain = rootDomain;
    }

    private String rootDomain;

    public String getRootDomain() {
        return rootDomain;
    }

    public void setRootDomain(String rootDomain) {
        this.rootDomain = rootDomain;
    }

    @Override
    public Map<String, String> tokenLogin(String token){
        String url = this.rootDomain + URL_TOKEN_LOGIN ;
        Map<String, String> result = new HashMap<>();
        CloseableHttpClient httpClient = null;
        try {
            CookieStore cookieStore = new BasicCookieStore();
            httpClient = HttpClients.custom()
                    .setDefaultCookieStore(cookieStore)
                    .build();

            URIBuilder builder = new URIBuilder(url);
            URI uri = builder.setParameter("token", token).build();

            HttpGet httpGet = new HttpGet(uri);
            CloseableHttpResponse httpResponse = null;
            try {
                httpResponse = httpClient.execute(httpGet);
                int code = httpResponse.getStatusLine().getStatusCode();
                if(code >= 400){
                    throw new HttpException("tokenLogin request error: code is " + code + ", result is " + EntityUtils.toString(httpResponse.getEntity()));
                }
                List<Cookie> cookieList = cookieStore.getCookies();
                for(Cookie c : cookieList){
                    if(SESSION_ID_NAME.equals(c.getName())){
                        result.put(SESSION_ID_NAME, c.getName());
                    }
                }
//                HttpEntity entity = httpResponse.getEntity();
//                result = EntityUtils.toString(entity);
            } catch (IOException e) {
                throw new HttpException("tokenLogin io Exception: ",  e);
            } finally {
                if(httpResponse != null){
                    httpResponse.close();
                }
            }
        } catch (Exception e) {
            throw new HttpException("tokenLogin Exception: ",  e);
        } finally {
            if(httpClient != null){
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
