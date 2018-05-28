package com.rishiqing.qywx.web.service.impl;

import com.rishiqing.qywx.service.common.crypto.CryptoUtil;
import com.rishiqing.qywx.service.model.corp.CorpStaffVO;
import com.rishiqing.qywx.web.service.RsqLoginService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author Wallace Mao
 * Date: 2018-05-23 20:34
 */
public class RsqLoginServiceImpl implements RsqLoginService {
    @Autowired
    private CryptoUtil cryptoUtil;

    @Override
    public String generateLoginToken(CorpStaffVO corpStaffVO) {
        String loginStr = makeLoginString(corpStaffVO);
        return cryptoUtil.encrypt(loginStr);
    }

    private String makeLoginString(CorpStaffVO corpStaffVO){
        return String.valueOf(new Date().getTime()) +
                "--" +
                corpStaffVO.getCorpId() +
                "--" +
                corpStaffVO.getUserId();
    }
}
