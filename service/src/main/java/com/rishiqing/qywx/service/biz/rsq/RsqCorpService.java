package com.rishiqing.qywx.service.biz.rsq;

import com.rishiqing.common.exception.HttpException;
import com.rishiqing.common.exception.RsqSyncException;
import com.rishiqing.common.exception.RsqUpdateNotExistsException;
import com.rishiqing.qywx.service.model.corp.CorpVO;

public interface RsqCorpService {
    public CorpVO pushCorp(CorpVO corpVO);
    public void pushCorpAll(CorpVO corpVO);
}
