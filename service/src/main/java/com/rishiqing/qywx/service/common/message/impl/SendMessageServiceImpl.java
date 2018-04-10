package com.rishiqing.qywx.service.common.message.impl;

import com.alibaba.fastjson.JSONObject;
import com.rishiqing.qywx.service.common.corp.CorpAppManageService;
import com.rishiqing.qywx.service.common.corp.CorpTokenManageService;
import com.rishiqing.qywx.service.common.isv.GlobalSuite;
import com.rishiqing.qywx.service.common.message.SendMessageService;
import com.rishiqing.qywx.service.model.corp.CorpAppVO;
import com.rishiqing.qywx.service.model.corp.CorpTokenVO;
import com.rishiqing.qywx.service.util.http.HttpUtilCorp;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Wallace Mao
 * Date: 2018-04-10 10:57
 */
public class SendMessageServiceImpl implements SendMessageService {
    @Autowired
    private HttpUtilCorp httpUtilCorp;
    @Autowired
    private GlobalSuite suite;
    @Autowired
    private CorpAppManageService corpAppManageService;
    @Autowired
    private CorpTokenManageService corpTokenManageService;
    /**
     * 发送文本卡片信息给指定的toUser
     * @param corpId，corpId
     * @param map，详见参考@see https://work.weixin.qq.com/api/doc#10167
     */
    @Override
    public void sendCorpMessage(String corpId, Map map) {
        String suiteKey = suite.getSuiteKey();
        CorpAppVO corpAppVO = corpAppManageService.getCorpAppBySuiteKeyAndCorpId(suiteKey, corpId);
        CorpTokenVO corpTokenVO = corpTokenManageService.getCorpToken(suiteKey, corpId);
        map.put("agentid", corpAppVO.getAgentId());
        httpUtilCorp.postSendMessage(corpTokenVO, map);
    }
}
