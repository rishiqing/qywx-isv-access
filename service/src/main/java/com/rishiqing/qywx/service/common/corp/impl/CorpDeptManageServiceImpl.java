package com.rishiqing.qywx.service.common.corp.impl;

import com.rishiqing.qywx.dao.mapper.corp.CorpDeptDao;
import com.rishiqing.qywx.service.common.corp.CorpDeptManageService;
import com.rishiqing.qywx.service.model.corp.CorpDeptVO;
import com.rishiqing.qywx.service.model.corp.helper.CorpDeptConverter;
import org.springframework.beans.factory.annotation.Autowired;

public class CorpDeptManageServiceImpl implements CorpDeptManageService {
    @Autowired
    private CorpDeptDao corpDeptDao;
    @Override
    public void saveOrUpdateCorpDept(CorpDeptVO corpDeptVO) {
        corpDeptDao.saveOrUpdateCorpDept(
                CorpDeptConverter.corpDeptVO2CorpDeptDO(corpDeptVO)
        );
    }
}
