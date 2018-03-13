package com.rishiqing.qywx.web.service;

import com.rishiqing.qywx.service.model.isv.SuitePreAuthCodeVO;

import java.util.Map;

/**
 * @author Wallace Mao
 * Date: 2018-03-12 18:42
 */
public interface IsvAuthService {
    SuitePreAuthCodeVO prepareAuth();
}
