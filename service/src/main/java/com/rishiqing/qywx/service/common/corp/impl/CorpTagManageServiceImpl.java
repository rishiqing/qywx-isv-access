package com.rishiqing.qywx.service.common.corp.impl;

import com.rishiqing.qywx.dao.mapper.corp.CorpTagDao;
import com.rishiqing.qywx.dao.model.corp.CorpTagCorpDeptDO;
import com.rishiqing.qywx.dao.model.corp.CorpTagCorpStaffDO;
import com.rishiqing.qywx.dao.model.corp.CorpTagDO;
import com.rishiqing.qywx.service.common.corp.CorpTagManageService;
import com.rishiqing.qywx.service.model.corp.CorpTagDetailVO;
import com.rishiqing.qywx.service.model.corp.helper.CorpTagConverter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2018-04-27 20:37
 */
public class CorpTagManageServiceImpl implements CorpTagManageService {
    @Autowired
    private CorpTagDao corpTagDao;

    @Override
    public void saveOrUpdateCorpTag(CorpTagDetailVO corpTagDetailVO){
        corpTagDao.saveOrUpdateCorpTag(
                CorpTagConverter.corpTagDetailVO2CorpTagDO(corpTagDetailVO)
        );
    }

    @Override
    public void saveOrUpdateCorpTagCorpStaff(CorpTagDetailVO corpTagDetailVO){
        List<CorpTagCorpStaffDO> list =  CorpTagConverter.corpTagDetailVO2CorpTagCorpStaffDOList(corpTagDetailVO);

        for(CorpTagCorpStaffDO staffDO : list){
            corpTagDao.saveOrUpdateCorpTagCorpStaff(staffDO);
        }
    }

    @Override
    public void saveOrUpdateCorpTagCorpDept(CorpTagDetailVO corpTagDetailVO){
        List<CorpTagCorpDeptDO> list = CorpTagConverter.corpTagDetailVO2CorpTagCorpDeptDOList(corpTagDetailVO);

        for(CorpTagCorpDeptDO deptDO : list){
            corpTagDao.saveOrUpdateCorpTagCorpDept(deptDO);
        }
    }
}
