package com.rishiqing.qywx.web.controller.corp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rishiqing.qywx.service.common.corp.CorpManageService;
import com.rishiqing.qywx.service.common.corp.CorpStaffManageService;
import com.rishiqing.qywx.service.common.isv.AppManageService;
import com.rishiqing.qywx.service.model.corp.CorpStaffVO;
import com.rishiqing.qywx.service.model.isv.AppVO;
import com.rishiqing.qywx.web.constant.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/corp")
public class CorpController {
    private static final Logger logger = LoggerFactory.getLogger("WEB_CALLBACK_LOGGER");
    @Autowired
    private CorpStaffManageService corpStaffManageService;

    @RequestMapping(value = "/staff", method = {RequestMethod.GET})
    @ResponseBody
    public Map getCorpStaff (
            @RequestParam("corpId") String corpId,
            @RequestParam("agentId") String agentId,
            @RequestParam("userId") String userId
    ) {
        logger.info("/corp/staff, corpId: {}, agentId: {}, userId: {}", corpId, agentId, userId);
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            CorpStaffVO corpStaffVO = corpStaffManageService.getCorpLoginStaffInfo(corpId, userId);
            result.put("user", corpStaffVO);
            result.put("errcode", ResultCode.NO_ERROR);
            result.put("errmsg", ResultCode.NO_ERROR_MSG);
        }catch(Exception e){
            logger.error("/corp/staff error: ", e);
            result.put("errcode", ResultCode.SYS_ERROR);
            result.put("errmsg", ResultCode.SYS_ERROR_MSG);
        }
        return result;
    }
}
