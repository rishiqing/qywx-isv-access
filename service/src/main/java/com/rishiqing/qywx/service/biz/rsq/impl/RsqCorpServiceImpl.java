package com.rishiqing.qywx.service.biz.rsq.impl;

import com.rishiqing.common.exception.RsqSyncException;
import com.rishiqing.common.model.RsqTeamVO;
import com.rishiqing.common.util.http.HttpUtilRsqSync;
import com.rishiqing.qywx.service.biz.rsq.RsqCorpService;
import com.rishiqing.qywx.service.biz.rsq.RsqDeptService;
import com.rishiqing.qywx.service.biz.rsq.RsqStaffService;
import com.rishiqing.qywx.service.common.corp.CorpManageService;
import com.rishiqing.qywx.service.common.isv.SuiteManageService;
import com.rishiqing.qywx.service.model.corp.CorpVO;
import com.rishiqing.qywx.service.model.corp.helper.CorpConverter;
import com.rishiqing.qywx.service.model.isv.SuiteVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @author Wallace Mao
 * Date: 2018-02-08 11:54
 */
public class RsqCorpServiceImpl implements RsqCorpService {
    private static final Logger logger = LoggerFactory.getLogger("SERVICE_CORP_PUSH_RSQ_LOGGER");

    @Autowired
    private Map isvGlobal;
    @Autowired
    private SuiteManageService suiteManageService;
    @Autowired
    private HttpUtilRsqSync httpUtilRsqSync;
    @Autowired
    private CorpManageService corpManageService;
    @Autowired
    private RsqDeptService rsqDeptService;
    @Autowired
    private RsqStaffService rsqStaffService;

    private SuiteVO suite;

    @PostConstruct
    private void init(){
        //  读取套件基本信息
        String suiteKey = (String)isvGlobal.get("suiteKey");
        this.suite = suiteManageService.getSuiteInfoByKey(suiteKey);
    }

    /**
     * 将单个企业的基本信息同步到日事清
     * @param corpVO
     * @return
     */
    @Override
    public CorpVO pushAndCreateCorp(CorpVO corpVO){
        //  如果rsqId存在，那么将不做任何处理
        if(null != corpVO.getRsqId()){
            return corpVO;
        }
        RsqTeamVO rsqTeamVO = CorpConverter.corpVO2RsqTeamVO(corpVO);
        try {
            RsqTeamVO team = httpUtilRsqSync.createCorp(this.suite.getRsqAppName(), this.suite.getRsqAppToken(), rsqTeamVO);
            corpVO.setRsqId(String.valueOf(team.getId()));
            corpManageService.saveOrUpdateCorp(corpVO);
        } catch (RsqSyncException e) {
            logger.error("push to create rishiqing team error: ", e);
            //TODO 做重试
        }
        return corpVO;
    }

    /**
     * 将corpVO所有的组织结构和人员信息都同步到日事清，用于在企业首次开通时的同步
     * @param corpVO
     */
    @Override
    public void pushAndCreateCorpAll(CorpVO corpVO){
        //  1  创建日事清企业
        pushAndCreateCorp(corpVO);

        //  2  创建企业部门
        rsqDeptService.pushAndCreateAllCorpDept(corpVO);

        //  3  创建企业部门成员
        rsqStaffService.pushAndCreateAllCorpStaff(corpVO);

        //  4  更新管理员状态
    }
}
