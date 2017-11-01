package com.rishiqing.qywx.web.controller.isv;

import com.rishiqing.qywx.service.model.isv.SuiteVO;
import com.rishiqing.qywx.web.exception.CallbackException;
import com.rishiqing.qywx.web.service.CallbackService;
import com.rishiqing.qywx.web.util.codec.AesException;
import com.rishiqing.qywx.web.util.codec.WXBizMsgCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/suite")
public class CallbackController {
    private static final Logger logger = LoggerFactory.getLogger("WEB_CALLBACK_LOGGER");

    @Autowired
    private CallbackService callbackService;

    @RequestMapping(value = "/receive", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String receiveMessage(
            @RequestParam("msg_signature") String msgSignature,
            @RequestParam("timestamp") String timestamp,
            @RequestParam("nonce") String nonce,
            @RequestParam(required = false, name = "echostr") String echoString,
            @RequestBody(required = false) String body
    ){
        logger.info("callback params signature: {}, timestamp: {}, nonce: {}, echoStr: {}, body: {}",
                msgSignature, timestamp, nonce, echoString, body);

        String resultStr = ""; //需要返回的明文
        try {
            //  根据echostr参数，判断是否是开启接受消息的接口
            if(echoString != null){
                //  走isv验证url流程
                resultStr = callbackService.verifyUrl(msgSignature, timestamp, nonce, echoString);
            }else{
                //  走isv接受消息的流程
                resultStr = callbackService.receiveMessage(msgSignature, timestamp, nonce, body);
            }
        } catch (CallbackException e) {
            logger.error("callback exception", e);
        } catch (Exception e) {
            //验证URL失败，错误原因请查看异常
            logger.error("unknown exception", e);
        }
        return resultStr;
    }
}
