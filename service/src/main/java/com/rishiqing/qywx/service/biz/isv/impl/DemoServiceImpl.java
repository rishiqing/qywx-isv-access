package com.rishiqing.qywx.service.biz.isv.impl;

import com.rishiqing.qywx.service.biz.isv.DemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DemoServiceImpl implements DemoService {
    private static final Logger webLogger = LoggerFactory.getLogger("WEB_LOGGER");
    private static final Logger serviceLogger = LoggerFactory.getLogger("SERVICE_LOGGER");
    private static final Logger classLogger = LoggerFactory.getLogger(DemoServiceImpl.class);

    public String printHelloWorld() {
        webLogger.info("this is webLogger from DemoService printHelloWorld");
        serviceLogger.info("this is serviceLogger from DemoService printHelloWorld");
        classLogger.error("this is classLogger from DemoService printHelloWorld");
        return "hello";
    }
}
