package com.rishiqing.qywx.web.controller.demo;

import com.rishiqing.qywx.service.common.crypto.CryptoUtil;
import com.rishiqing.qywx.service.model.corp.CorpStaffVO;
import com.rishiqing.qywx.web.demo.DemoService;
import com.rishiqing.qywx.service.common.isv.SuiteManageService;
import com.rishiqing.qywx.service.event.message.mq.DemoMessage;
import com.rishiqing.qywx.service.event.service.QueueService;
import com.rishiqing.qywx.service.model.isv.SuiteVO;
import com.rishiqing.qywx.web.service.RsqLoginService;
import com.rishiqing.qywx.web.util.codec.AesException;
import com.rishiqing.qywx.web.util.codec.WXBizMsgCrypt;
import com.rishiqing.qywx.web.util.common.XmlUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/demo")
public class DemoController {
    private static final Logger webLogger = LoggerFactory.getLogger("WEB_CALLBACK_LOGGER");
    private static final Logger consoleLogger = LoggerFactory.getLogger("CONSOLE_LOGGER");

    @Autowired
    private Map isvGlobal;
    @Autowired
    private SuiteManageService suiteManageService;
    @Autowired
    private DemoService demoService;
    @Autowired
    private QueueService queueService;
    @Autowired
    private CryptoUtil cryptoUtil;
    @Autowired
    private RsqLoginService rsqLoginService;

    @RequestMapping("/encode")
    @ResponseBody
    public String demoCall(){
        String body = "<xml><ToUserName><![CDATA[tj146dbe5cecf74725]]></ToUserName>\n" +
                "<Encrypt><![CDATA[29aD1J5vUI26NUirhzKcOYWXzI1hYV1/W7oW6JkUKCaXXYw+pcH8jQSvOeqr9WZT7fjvs42ZO731DKRvhY2QiqNMRfCzOS4Iuim3kCNx0lK1aHJhkkD6TWUbr+UlstkA7uK/TrjZPKrEOkc9dcEFmTyPo7fVRczioaYkqt0WIprayF+T7X/gs3BqRltnc8yVI0zKL2KaYZXlJvBNk6h6i3yYeiOw4N6OxMVlSXLqOORiEHWB0qsJiRBhGHTs0nB220KZAaGDhEWWDI3E0dMmt8r5ERyuxKO4OcPM50als7pPkP1twotZLBvo9tvR971yhSVcJpvr7+PXdOi/98Bojf34pt0DdraPqwUxVweGVgrjtW8ZVk531vNQHuxC2dDM]]></Encrypt>\n" +
                "<AgentID><![CDATA[]]></AgentID>\n" +
                "</xml>";

        String suiteKey = (String)isvGlobal.get("suiteKey");
        SuiteVO suiteVO = suiteManageService.getSuiteInfoByKey(suiteKey);

        String token = suiteVO.getToken();
        String encodingAesKey = suiteVO.getEncodingAesKey();

        String signature = "a192281bb9d8f969cd2a615eb6d44d9c6ac6f8de";
        String timestamp = "1509108038";
        String nonce = "2082290525";

        try {
            WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(token, encodingAesKey, suiteKey);

            String str = wxcpt.decryptMsg(signature, timestamp, nonce, body);
            System.out.println(str);

            Map map = XmlUtil.simpleXmlString2Map(str);
            System.out.println("ticket----" + map.get("SuiteTicket"));

        } catch (AesException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }


        return "success";
    }

    @RequestMapping("/log4j")
    @ResponseBody
    public String demoLog(){
        webLogger.info("this is webLogger from demoLogController");
        consoleLogger.info("this is consoleLogger from demoLogController");
        return demoService.printHelloWorld();
    }

    @RequestMapping("/send/event")
    @ResponseBody
    public String sendEvent(){
        demoService.sendAsyncEvent();
        return "success";
    }

    @RequestMapping("/send/mq")
    @ResponseBody
    public String sendMq(){
        System.out.println("=======begin");
        for(int i = 0; i < 1; i++ ){
            queueService.sendToDemo(new DemoMessage("xxxxxx-" + i, "hello mq message"));
        }
        System.out.println("=======end");
        return "success";
    }

    @RequestMapping(value = "/send/test", produces = "text/plain")
    @ResponseBody
    public String sendString(){
        Date d = new Date();
        return "success: " + d;
    }

    @RequestMapping("/getToken")
    @ResponseBody
    public String getToken(
            @RequestParam("outerId") String outerId
    ){
        try {
            Date d = new Date();
            String loginStr = d.getTime() + "--" + outerId;
            String cryptEncoded = cryptoUtil.encrypt(loginStr);
            System.out.println("crypt Encoded is: " + cryptEncoded + ", length is " + cryptEncoded.length());

            String urlEncoded = URLEncoder.encode(cryptEncoded, "UTF-8");
            System.out.println("url Encoded url: " + urlEncoded);

            String urlDecoded = URLDecoder.decode(urlEncoded, "UTF-8");
            System.out.println("url Decoded url: " + urlDecoded);

            System.out.println("crypt Decoded is: " + cryptoUtil.decrypt(urlDecoded));
            return "token is: " + urlEncoded;
        } catch (Exception e) {
            e.printStackTrace();
            return "failed";
        }
    }

    @RequestMapping("/tokenLogin")
    @ResponseBody
    public String tokenLogin(
            @RequestParam("corpId") String corpId,
            @RequestParam("userId") String userId
    ){
        String result;
        CloseableHttpClient httpClient = null;
        try {
            CorpStaffVO corpStaffVO = new CorpStaffVO();
            corpStaffVO.setCorpId(corpId);
            corpStaffVO.setUserId(userId);
            String orgToken = "HaTify+mKHb2fumR1s1YPgHf7C68euZ9FR8Ltuywxm2SXf6oG1BzPXWSlQ1S9cuu";
            String newToken = URLEncoder.encode(
                    "HaTify+mKHb2fumR1s1YPgHf7C68euZ9FR8Ltuywxm2SXf6oG1BzPXWSlQ1S9cuu",
                    "UTF-8"
            );
            String token = orgToken;

            CookieStore cookieStore = new BasicCookieStore();
            httpClient = HttpClients.custom()
                    .setDefaultCookieStore(cookieStore)
                    .build();

            System.out.println("token is: " + token);

//        String path = "https://www.rishiqing.com/task/qywxOauth/tokenLogin";

            URI uri = null;
            URIBuilder builder = new URIBuilder("https://www.rishiqing.com/task/qywxOauth/tokenLogin");
            uri = builder.setParameter("token", token).build();

            HttpGet httpGet = new HttpGet(uri);
            CloseableHttpResponse httpResponse = null;
            try {
                httpResponse = httpClient.execute(httpGet);
                HttpEntity entity = httpResponse.getEntity();
                String respBody = EntityUtils.toString(entity);

                System.out.println("cookieStore: " + cookieStore.getCookies());
                System.out.println("status line: " + httpResponse.getStatusLine());
                System.out.println("entity: " + respBody);

                result = respBody;
            } catch (IOException e) {
                e.printStackTrace();
                result = "fail";
            } finally {
                if(httpResponse != null){
                    httpResponse.close();
                }
            }
        } catch (Exception e) {
            result = "fail";
            e.printStackTrace();
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

    public static void main(String[] args) {
        try {
            String org = "XuCp4LccwE6t8uCnZLy26BBntvqHQEPB0H0Gnn9a5+8=";
//            String encoded = URLEncoder.encode(org, "UTF-8");
//            System.out.println("encoded: " + encoded);
//
//            String decoded = URLDecoder.decode(encoded, "UTF-8");
//            System.out.println("decoded: " + decoded);

            String encodedUrl = URLEncoder.encode(org, "UTF-8");
            System.out.println("after encoded url: " + encodedUrl);

            String decodedUrl = URLDecoder.decode(encodedUrl, "UTF-8");
            System.out.println("after decoded url: " + decodedUrl);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
