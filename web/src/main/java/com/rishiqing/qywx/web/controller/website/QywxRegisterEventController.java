package com.rishiqing.qywx.web.controller.website;

import com.rishiqing.common.exception.HttpException;
import com.rishiqing.qywx.web.service.website.QywxRegisterEventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author Wallace Mao
 * Date: 2018-05-12 15:42
 * 注册定制化相关
 * @see [https://work.weixin.qq.com/api/doc#11729/%E6%B3%A8%E5%86%8C%E5%AE%8C%E6%88%90%E5%9B%9E%E8%B0%83%E4%BA%8B%E4%BB%B6]
 */
@Controller
@RequestMapping("/systemEvent")
public class QywxRegisterEventController {
    private static Logger logger = LoggerFactory.getLogger("WEB_OAUTH_LOGGER");

    @Autowired
    private QywxRegisterEventService qywxRegisterEventService;

    @RequestMapping(value = "/receive", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String receive(
            @RequestParam("msg_signature") String msgSignature,
            @RequestParam("timestamp") String timestamp,
            @RequestParam("nonce") String nonce,
            @RequestParam(required = false, name = "echostr") String echoString,
            @RequestBody(required = false) String body
    ){
        logger.info("----systemEvent receive----signature: {}, timestamp: {}, nonce: {}, echoString: {}, body: {}", msgSignature, timestamp, nonce, echoString, body);
        String resultStr = ""; //需要返回的明文
        try {
            //  根据echostr参数，判断是否是开启接受消息的接口
            if(echoString != null){
                //  走isv验证url流程
                resultStr = qywxRegisterEventService.verifyUrl(msgSignature, timestamp, nonce, echoString);
            }else{
                //  走isv接受消息的流程
                resultStr = qywxRegisterEventService.receiveMessage(msgSignature, timestamp, nonce, body);
            }
            logger.info("callback receive response: {}", resultStr);
        } catch (Exception e) {
            //验证URL失败，错误原因请查看异常
            logger.error("callback exception", e);
            resultStr = "fail";
        }
        return resultStr;
    }
}
