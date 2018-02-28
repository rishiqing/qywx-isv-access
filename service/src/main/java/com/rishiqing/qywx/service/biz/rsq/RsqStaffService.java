package com.rishiqing.qywx.service.biz.rsq;

import com.rishiqing.common.exception.HttpException;
import com.rishiqing.common.exception.RsqSyncException;
import com.rishiqing.common.exception.RsqUpdateNotExistsException;
import com.rishiqing.qywx.service.model.corp.CorpDeptVO;
import com.rishiqing.qywx.service.model.corp.CorpStaffVO;
import com.rishiqing.qywx.service.model.corp.CorpVO;

import java.util.List;

public interface RsqStaffService {

    void pushAndCreateAllCorpStaff(CorpVO corpVO) throws RsqSyncException, HttpException, RsqUpdateNotExistsException;

    CorpStaffVO pushAndCreateStaff(CorpVO corpVO, List<CorpDeptVO> corpDeptVOList, CorpStaffVO corpStaffVO) throws RsqSyncException;

    CorpStaffVO pushAndUpdateStaff(CorpVO corpVO, List<CorpDeptVO> corpDeptVOList, CorpStaffVO corpStaffVO) throws RsqUpdateNotExistsException, RsqSyncException;

    CorpStaffVO pushAndDeleteStaffFromTeam(CorpVO corpVO, CorpStaffVO corpStaffVO) throws RsqUpdateNotExistsException, RsqSyncException;

    CorpStaffVO pushAndSetStaffAdmin(CorpVO corpVO, CorpStaffVO corpStaffVO) throws RsqUpdateNotExistsException, HttpException, RsqSyncException;
}
