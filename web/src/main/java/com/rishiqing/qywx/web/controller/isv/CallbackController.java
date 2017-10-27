package com.rishiqing.qywx.web.controller.isv;

import com.rishiqing.qywx.service.biz.isv.SuiteManageService;
import com.rishiqing.qywx.service.model.isv.SuiteVO;
import com.rishiqing.qywx.web.util.codec.AesException;
import com.rishiqing.qywx.web.util.codec.WXBizMsgCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/suite")
public class CallbackController {
    private static final Logger logger = LoggerFactory.getLogger("ISV_CALLBACK_LOGGER");

    @Autowired
    private Map isvGlobal;
    @Autowired
    private SuiteManageService suiteManageService;

    @RequestMapping(value = "/receive", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String receiveMessage(
            @RequestParam("msg_signature") String sVerifyMsgSig,
            @RequestParam("timestamp") String sVerifyTimeStamp,
            @RequestParam("nonce") String sVerifyNonce,
            @RequestParam(required = false, name = "echostr") String sVerifyEchoStr,
            @RequestBody String body
    ){
        logger.info("callback params signature: {}, timestamp: {}, nonce: {}, echoStr: {}, body: {}",
                sVerifyMsgSig, sVerifyTimeStamp, sVerifyNonce, sVerifyEchoStr, body);

        String suiteKey = (String)isvGlobal.get("suiteKey");
        SuiteVO suiteVO = suiteManageService.getSuiteInfoByKey(suiteKey);

        String sToken = suiteVO.getToken();
        String sCorpID = suiteVO.getCorpId();
        String sEncodingAESKey = suiteVO.getEncodingAesKey();

        String resultStr = "success"; //需要返回的明文
        try {
            WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(sToken, sEncodingAESKey, sCorpID);

            if(sVerifyEchoStr != null){
                //  走isv验证url流程
                resultStr = wxcpt.verifyURL(sVerifyMsgSig, sVerifyTimeStamp,
                        sVerifyNonce, sVerifyEchoStr);
            }else{
                //  走isv接受消息的流程

            }

        } catch (AesException e) {
            logger.error("exception in receiveMessage:", e);
            e.printStackTrace();
        } catch (Exception e) {
            //验证URL失败，错误原因请查看异常
            logger.error("callback exception", e);
            e.printStackTrace();
        }
        return resultStr;
    }
}
