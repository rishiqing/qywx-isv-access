package com.rishiqing.qywx.web.controller.demo;

import com.rishiqing.qywx.service.biz.isv.SuiteManageService;
import com.rishiqing.qywx.service.model.isv.SuiteVO;
import com.rishiqing.qywx.web.util.codec.AesException;
import com.rishiqing.qywx.web.util.codec.WXBizMsgCrypt;
import com.rishiqing.qywx.web.util.common.XmlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private Map isvGlobal;
    @Autowired
    private SuiteManageService suiteManageService;

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
}
