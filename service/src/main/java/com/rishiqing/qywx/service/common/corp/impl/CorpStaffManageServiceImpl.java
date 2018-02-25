package com.rishiqing.qywx.service.common.corp.impl;

import com.rishiqing.qywx.dao.mapper.corp.CorpStaffDao;
import com.rishiqing.qywx.dao.mapper.corp.RsqInfoDao;
import com.rishiqing.qywx.dao.model.corp.CorpStaffDO;
import com.rishiqing.qywx.service.common.corp.CorpStaffManageService;
import com.rishiqing.qywx.service.model.corp.CorpStaffVO;
import com.rishiqing.qywx.service.model.corp.helper.CorpStaffConverter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CorpStaffManageServiceImpl implements CorpStaffManageService {
    @Autowired
    private CorpStaffDao corpStaffDao;
    @Autowired
    private RsqInfoDao rsqInfoDao;
    @Override
    public void saveOrUpdateCorpStaff(CorpStaffVO corpStaffVO) {
        corpStaffDao.saveOrUpdateCorpStaff(
                CorpStaffConverter.corpStaffVO2CorpStaffDO(corpStaffVO)
        );
    }

    @Override
    public void updateRsqInfo(CorpStaffVO corpStaffVO) {
        rsqInfoDao.updateCorpStaffRsqInfo(
                CorpStaffConverter.corpStaffVO2CorpStaffDO(corpStaffVO)
        );
    }

    @Override
    public List<CorpStaffVO> listCorpStaffByCorpId(String corpId){
        return CorpStaffConverter.corpStaffDOList2CorpStaffVOList(
                corpStaffDao.listCorpStaffByCorpId(corpId)
        );
    }

    @Override
    public CorpStaffVO getCorpLoginStaffInfo(String corpId, String userId) {
        return CorpStaffConverter.corpStaffDO2CorpStaffVO(
                corpStaffDao.getCorpStaffByCorpIdAndUserId(corpId, userId)
        );
    }

    @Override
    public CorpStaffVO getCorpStaffByCorpIdAndUserId(String corpId, String userId) {
        CorpStaffDO corpStaffDO = corpStaffDao.getCorpStaffByCorpIdAndUserId(corpId, userId);
        return CorpStaffConverter.corpStaffDO2CorpStaffVO(corpStaffDO);
    }

    @Override
    public void deleteCorpStaffByCorpIdAndUserId(String corpId, String userId) {
        corpStaffDao.removeCorpStaffByCorpIdAndUserId(corpId, userId);
    }
}
