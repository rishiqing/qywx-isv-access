package com.rishiqing.qywx.service.biz.rsq;

import com.rishiqing.common.exception.RsqSyncException;
import com.rishiqing.common.exception.RsqUpdateNotExistsException;
import com.rishiqing.qywx.service.model.corp.CorpDeptVO;
import com.rishiqing.qywx.service.model.corp.CorpVO;

public interface RsqDeptService {
    void pushAllCorpDept(CorpVO corpVO);
    CorpDeptVO pushAndCreateDept(CorpVO corpVO, CorpDeptVO parentCorpDeptVO, CorpDeptVO corpDeptVO);
    CorpDeptVO pushAndUpdateDept(CorpVO corpVO, CorpDeptVO parentCorpDeptVO, CorpDeptVO corpDeptVO);
    CorpDeptVO pushAndDeleteDept(CorpVO corpVO, CorpDeptVO corpDeptVO);
}
