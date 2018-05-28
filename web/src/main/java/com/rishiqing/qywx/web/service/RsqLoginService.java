package com.rishiqing.qywx.web.service;

import com.rishiqing.qywx.service.model.corp.CorpStaffVO;

/**
 * @author Wallace Mao
 * Date: 2018-05-23 20:34
 */
public interface RsqLoginService {
    String generateLoginToken(CorpStaffVO corpStaffVO);
}
