package com.rishiqing.qywx.web.controller.system;

import com.alibaba.fastjson.JSONObject;
import com.rishiqing.qywx.service.callback.FetchCallbackHandler;
import com.rishiqing.qywx.service.callback.PushCallbackHandler;
import com.rishiqing.qywx.service.common.corp.CorpSuiteManageService;
import com.rishiqing.qywx.service.constant.CallbackInfoType;
import com.rishiqing.qywx.service.exception.CallbackException;
import com.rishiqing.qywx.web.util.common.XmlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Map;

/**
 * @author Wallace Mao
 * Date: 2018-06-01 14:43
 */
@Controller
@RequestMapping("/manage")
public class DateManageController {
    private static final Logger webLogger = LoggerFactory.getLogger("WEB_CALLBACK_LOGGER");

    @Autowired
    private Map isvGlobal;
    @Autowired
    private FetchCallbackHandler fetchCallbackHandler;
    @Autowired
    private PushCallbackHandler pushCallbackHandler;

    /**
     * 获取企业微信的授权信息，并更新到本地
     * @param corpId
     * @return
     */
    @RequestMapping("/fetchCorpInfo")
    @ResponseBody
    public String fetchCorpInfo(
            @RequestParam("corpId") String corpId
    ){
        try {
            fetchCallbackHandler.handleFetchCorp(corpId);
            return "success";
        } catch (Exception e) {
            webLogger.error("/manage/fetchCorpInfo error: ", e);
            return "failed";
        }
    }

    /**
     * 将企业的信息同步到本地，先实现所有将企业所有信息同步到日事清
     * @param corpId
     * @param type
     * @param deptId
     * @param staffId
     * @return
     */
    @RequestMapping("/pushCorpInfoToRishiqing")
    @ResponseBody
    public String pushCorpInfo(
            @RequestParam("corpId") String corpId,
            @RequestParam("type") String type,
            @RequestParam(value = "deptId", required = false) String deptId,
            @RequestParam(value = "staffId", required = false) String staffId
    ){
        try {
            switch (type) {
                case "create_corp":
                    pushCallbackHandler.handlePushCorp(corpId);
                    break;
                case "create_user":
                    break;
                case "update_user":
                    break;
                case "delete_user":
                    break;
                case "create_party":
                    break;
                case "update_party":
                    break;
                case "delete_party":
                    break;
                default:
                    //  对于不识别的infoType，直接抛出异常
                    throw new CallbackException("type not handled: " + type);
            }
            return "success";
        } catch (Exception e) {
            webLogger.error("/manage/pushCorpInfoToRishiqing error: ", e);
            return "failed";
        }
    }
}
