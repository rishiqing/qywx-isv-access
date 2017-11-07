package com.rishiqing.qywx.service.common.corp.impl;

import com.rishiqing.qywx.dao.mapper.corp.CorpStaffDao;
import com.rishiqing.qywx.dao.model.corp.CorpStaffDO;
import com.rishiqing.qywx.service.common.corp.CorpStaffManageService;
import com.rishiqing.qywx.service.model.corp.CorpStaffVO;
import com.rishiqing.qywx.service.model.corp.helper.CorpStaffConverter;
import org.springframework.beans.factory.annotation.Autowired;

public class CorpStaffManageServiceImpl implements CorpStaffManageService {
    @Autowired
    private CorpStaffDao corpStaffDao;
    @Override
    public void saveOrUpdateCorpStaff(CorpStaffVO corpStaffVO) {
        corpStaffDao.saveOrUpdateCorpStaff(
                CorpStaffConverter.corpStaffVO2CorpStaffDO(corpStaffVO)
        );
    }

    @Override
    public CorpStaffVO getCorpLoginStaffInfo(String corpId, String userId) {
        return CorpStaffConverter.corpStaffDO2CorpStaffVO(
                corpStaffDao.getCorpStaffByCorpIdAndUserId(corpId, userId)
        );
    }
}
