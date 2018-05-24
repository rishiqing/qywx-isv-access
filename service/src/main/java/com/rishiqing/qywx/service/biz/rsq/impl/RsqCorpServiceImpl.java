package com.rishiqing.qywx.service.biz.rsq.impl;

import com.rishiqing.common.model.RsqTeamVO;
import com.rishiqing.common.util.http.HttpUtilRsqSync;
import com.rishiqing.qywx.service.biz.rsq.RsqCorpService;
import com.rishiqing.qywx.service.biz.rsq.RsqDeptService;
import com.rishiqing.qywx.service.biz.rsq.RsqStaffService;
import com.rishiqing.qywx.service.common.corp.CorpManageService;
import com.rishiqing.qywx.service.common.isv.GlobalSuite;
import com.rishiqing.qywx.service.common.rsq.RsqInfoManageService;
import com.rishiqing.qywx.service.model.corp.CorpVO;
import com.rishiqing.qywx.service.model.corp.helper.CorpConverter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Wallace Mao
 * Date: 2018-02-08 11:54
 */
public class RsqCorpServiceImpl implements RsqCorpService {

    @Autowired
    private GlobalSuite suite;
    @Autowired
    private HttpUtilRsqSync httpUtilRsqSync;
    @Autowired
    private CorpManageService corpManageService;
    @Autowired
    private RsqInfoManageService rsqInfoManageService;
    @Autowired
    private RsqDeptService rsqDeptService;
    @Autowired
    private RsqStaffService rsqStaffService;

    /**
     * 将单个企业的基本信息同步到日事清
     * @param corpVO
     * @return
     */
    @Override
    public CorpVO pushCorp(CorpVO corpVO) {
        //  如果rsqId存在，那么将不做任何处理
        if(null != corpVO.getRsqId()){
            return corpVO;
        }
        RsqTeamVO rsqTeamVO = CorpConverter.corpVO2RsqTeamVO(corpVO);
        RsqTeamVO team = httpUtilRsqSync.createCorp(suite.getRsqAppName(), suite.getRsqAppToken(), rsqTeamVO);
        corpVO.setRsqId(String.valueOf(team.getId()));
        rsqInfoManageService.updateCorpRsqInfo(corpVO);
        return corpVO;
    }

    /**
     * 将corpVO所有的组织结构和人员信息都同步到日事清，在企业首次授权和授权变更时都会进行一次同步
     * 这里需要注意的是：由于用户在可见范围选择的时候可能选择的只是部分部门和部分用户，因此这里需要做兼容处理
     * 具体的同步方案参照rsqDeptService.pushAllCorpDept和rsqStaffService.pushAllCorpStaff方法中的说明
     * @param corpVO
     */
    @Override
    public void pushCorpAll(CorpVO corpVO) {

        //  1  创建或更新日事清企业
        pushCorp(corpVO);
        //  2  创建企业部门
        rsqDeptService.pushAllCorpDept(corpVO);
        //  3  创建企业部门成员，同时会更新管理员状态
        rsqStaffService.pushAllCorpStaff(corpVO);
    }
}
