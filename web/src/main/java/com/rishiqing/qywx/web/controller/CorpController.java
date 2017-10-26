package com.rishiqing.qywx.web.controller;

import com.rishiqing.qywx.service.biz.corp.CorpManageService;
import com.rishiqing.qywx.service.biz.isv.AppManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CorpController {
    private static final Logger logger = LoggerFactory.getLogger("ISV_CALLBACK_LOGGER");
    @Autowired
    private CorpManageService corpManageService;
    @Autowired
    private AppManageService appManageService;

    @RequestMapping(value = "/hello", method = {RequestMethod.GET})
    @ResponseBody
    public String getCorp () {
//        return corpManageService.getCorpInfo().getCorpName();
        logger.error("Temperature set to {}. Old temperature was {}.", 1, 2);
        List list = appManageService.listAllApp();
        return "" + list.size();
    }
}
