package com.rishiqing.qywx.service.biz.rsq;

import com.rishiqing.common.exception.RsqSyncException;
import com.rishiqing.common.exception.RsqUpdateNotExistsException;
import com.rishiqing.qywx.service.model.corp.CorpDeptVO;
import com.rishiqing.qywx.service.model.corp.CorpVO;

public interface RsqDeptService {
    void pushAndCreateAllCorpDept(CorpVO corpVO) throws RsqSyncException;
    CorpDeptVO pushAndCreateDept(CorpVO corpVO, CorpDeptVO parentCorpDeptVO, CorpDeptVO corpDeptVO) throws RsqSyncException;
    CorpDeptVO pushAndUpdateDept(CorpVO corpVO, CorpDeptVO parentCorpDeptVO, CorpDeptVO corpDeptVO) throws RsqSyncException, RsqUpdateNotExistsException;
    CorpDeptVO pushAndDeleteDept(CorpVO corpVO, CorpDeptVO corpDeptVO) throws RsqSyncException, RsqUpdateNotExistsException;
}
