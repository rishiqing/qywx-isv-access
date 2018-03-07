package com.rishiqing.qywx.service.biz.rsq.impl;

import com.rishiqing.qywx.dao.mapper.corp.CorpStaffDao;
import com.rishiqing.qywx.service.biz.rsq.IdMapService;
import com.rishiqing.qywx.service.model.corp.CorpStaffIdsVO;
import com.rishiqing.qywx.service.model.corp.helper.CorpStaffIdsConverter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: user 毛文强
 * Date: 2017/11/20
 * Time: 14:39
 * To change this template use File | Settings | File Templates.
 */
public class IdMapServiceImpl implements IdMapService {
    @Autowired
    private CorpStaffDao corpStaffDao;
    @Override
    public List<CorpStaffIdsVO> getRsqIdFromUserId(String corpId, String[] userIds) {
        return CorpStaffIdsConverter.CorpStaffIdsDOList2CorpStaffIdsVOList(
                corpStaffDao.getRsqIdFromUserId(corpId, userIds)
        );
    }

    @Override
    public List<CorpStaffIdsVO> getUserIdFromRsqId(String corpId, String[] rsqIds) {
        return CorpStaffIdsConverter.CorpStaffIdsDOList2CorpStaffIdsVOList(
                corpStaffDao.getUserIdFromRsqId(corpId, rsqIds)
        );
    }
}
