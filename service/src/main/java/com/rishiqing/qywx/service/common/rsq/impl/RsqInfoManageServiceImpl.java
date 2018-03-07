package com.rishiqing.qywx.service.common.rsq.impl;

import com.rishiqing.qywx.dao.mapper.rsq.RsqInfoDao;
import com.rishiqing.qywx.service.common.rsq.RsqInfoManageService;
import com.rishiqing.qywx.service.model.corp.CorpDeptVO;
import com.rishiqing.qywx.service.model.corp.CorpStaffVO;
import com.rishiqing.qywx.service.model.corp.CorpVO;
import com.rishiqing.qywx.service.model.corp.helper.CorpConverter;
import com.rishiqing.qywx.service.model.corp.helper.CorpDeptConverter;
import com.rishiqing.qywx.service.model.corp.helper.CorpStaffConverter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Wallace Mao
 * Date: 2018-02-27 17:59
 */
public class RsqInfoManageServiceImpl implements RsqInfoManageService {
    @Autowired
    private RsqInfoDao rsqInfoDao;
    @Override
    public void updateCorpRsqInfo(CorpVO corpVO) {
        rsqInfoDao.updateCorpRsqInfo(
                CorpConverter.corpVO2CorpDO(corpVO)
        );
    }

    @Override
    public void updateCorpDeptRsqInfo(CorpDeptVO corpDeptVO) {
        rsqInfoDao.updateCorpDeptRsqInfo(
                CorpDeptConverter.corpDeptVO2CorpDeptDO(corpDeptVO)
        );
    }

    @Override
    public void updateCorpStaffRsqInfo(CorpStaffVO corpStaffVO) {
        rsqInfoDao.updateCorpStaffRsqInfo(
                CorpStaffConverter.corpStaffVO2CorpStaffDO(corpStaffVO)
        );
    }
}
