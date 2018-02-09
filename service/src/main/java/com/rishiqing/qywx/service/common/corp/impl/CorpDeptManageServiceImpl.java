package com.rishiqing.qywx.service.common.corp.impl;

import com.rishiqing.qywx.dao.mapper.corp.CorpDeptDao;
import com.rishiqing.qywx.dao.model.corp.CorpDeptDO;
import com.rishiqing.qywx.service.common.corp.CorpDeptManageService;
import com.rishiqing.qywx.service.model.corp.CorpDeptVO;
import com.rishiqing.qywx.service.model.corp.helper.CorpDeptConverter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CorpDeptManageServiceImpl implements CorpDeptManageService {
    @Autowired
    private CorpDeptDao corpDeptDao;

    @Override
    public List<CorpDeptVO> listCorpDeptListByCorpIdAndParentId(String corpId, Long parentId){
        List<CorpDeptDO> doList = corpDeptDao.listCorpDeptByCorpIdAndParentId(corpId, parentId);
        return CorpDeptConverter.corpDeptDOList2CorpDeptVOList(doList);
    }

    @Override
    public CorpDeptVO getCorpDeptByCorpIdAndDeptId(String corpId, Long deptId) {
        CorpDeptDO corpDeptDO = corpDeptDao.getCorpDeptByCorpIdAndDeptId(corpId, deptId);
        return CorpDeptConverter.corpDeptDO2CorpDeptVO(corpDeptDO);
    }

    @Override
    public void saveOrUpdateCorpDept(CorpDeptVO corpDeptVO) {
        corpDeptDao.saveOrUpdateCorpDept(
                CorpDeptConverter.corpDeptVO2CorpDeptDO(corpDeptVO)
        );
    }

    @Override
    public void deleteCorpDeptByCorpIdAndDeptId(String corpId, Long deptId) {
        corpDeptDao.removeCorpDeptByCorpIdAndDeptId(corpId, deptId);
    }
}
