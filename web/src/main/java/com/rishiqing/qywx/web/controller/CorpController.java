package com.rishiqing.qywx.web.controller;

import com.rishiqing.qywx.biz.corp.CorpManageService;
import com.rishiqing.qywx.biz.corp.impl.CorpManageServiceImpl;
import com.rishiqing.qywx.biz.suite.AppManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class CorpController {
    @Autowired
    private CorpManageService corpManageService;
    @Autowired
    private AppManageService appManageService;

    @RequestMapping(value = "/hello", method = {RequestMethod.GET})
    @ResponseBody
    public String getCorp () {
//        return corpManageService.getCorpInfo().getCorpName();
        List list = appManageService.listAllApp();
        System.out.println("=========" + list.size());
        return "" + list.size();
    }
}
