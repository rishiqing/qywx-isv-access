package com.rishiqing.qywx.service.biz.rsq.impl;

import com.rishiqing.common.model.RsqCommonUserVO;
import com.rishiqing.common.model.RsqTeamVO;
import com.rishiqing.common.util.http.HttpUtilRsqSync;
import com.rishiqing.qywx.service.biz.rsq.RsqCorpService;
import com.rishiqing.qywx.service.biz.rsq.RsqDeptService;
import com.rishiqing.qywx.service.biz.rsq.RsqStaffService;
import com.rishiqing.qywx.service.common.corp.CorpManageService;
import com.rishiqing.qywx.service.common.corp.CorpStaffManageService;
import com.rishiqing.qywx.service.common.corp.CorpSuiteManageService;
import com.rishiqing.qywx.service.common.isv.GlobalSuite;
import com.rishiqing.qywx.service.common.rsq.RsqInfoManageService;
import com.rishiqing.qywx.service.model.corp.CorpStaffVO;
import com.rishiqing.qywx.service.model.corp.CorpSuiteVO;
import com.rishiqing.qywx.service.model.corp.CorpVO;
import com.rishiqing.qywx.service.model.corp.helper.CorpConverter;
import com.rishiqing.qywx.service.model.corp.helper.CorpStaffConverter;
import com.rishiqing.qywx.service.util.rsq.UserGenerator;
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
    private CorpSuiteManageService corpSuiteManageService;
    @Autowired
    private CorpStaffManageService corpStaffManageService;
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

        //  当有授权管理员时，将授权管理作为创建者传入到日事清后台接口中
        String suiteKey = suite.getSuiteKey();
        CorpSuiteVO corpSuiteVO = corpSuiteManageService.getCorpSuite(suiteKey, corpVO.getCorpId());
        CorpStaffVO creator = null;
        RsqCommonUserVO rsqCreator = null;
        if(corpSuiteVO.getAuthUserId() != null){
            creator = corpStaffManageService.getCorpStaffByCorpIdAndUserId(
                    corpVO.getCorpId(),
                    corpSuiteVO.getAuthUserId());
            if(creator != null){
                rsqCreator = CorpStaffConverter.corpStaffVO2RsqCommonUserVO(corpVO, null, creator);
                //  自动生成用户名和密码
                String password = UserGenerator.generateRsqPassword(suite.getRsqAppName());
                rsqCreator.setUsername(UserGenerator.generateRsqUsername(suite.getRsqAppName()));
                rsqCreator.setPassword(password);
            }
        }

        RsqTeamVO rsqTeamVO = CorpConverter.corpVO2RsqTeamVO(corpVO);
        rsqTeamVO.setCreator(rsqCreator);

        RsqTeamVO team = httpUtilRsqSync.createCorp(suite.getRsqAppName(), suite.getRsqAppToken(), rsqTeamVO);

        corpVO.setRsqId(String.valueOf(team.getId()));
        rsqInfoManageService.updateCorpRsqInfo(corpVO);

        //  当有授权管理员时，需要同时更新授权管理员的用户信息
        if(creator != null && team.getCreator() != null){
            RsqCommonUserVO rsqResultCreator = team.getCreator();
            creator.setRsqUserId(String.valueOf(rsqResultCreator.getId()));
            creator.setRsqUsername(rsqCreator.getUsername());
            creator.setRsqPassword(rsqCreator.getPassword());
            rsqInfoManageService.updateCorpStaffRsqInfo(creator);
        }
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
