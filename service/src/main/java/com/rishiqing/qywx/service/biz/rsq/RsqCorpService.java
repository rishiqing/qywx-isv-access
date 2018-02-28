package com.rishiqing.qywx.service.biz.rsq;

import com.rishiqing.common.exception.HttpException;
import com.rishiqing.common.exception.RsqSyncException;
import com.rishiqing.common.exception.RsqUpdateNotExistsException;
import com.rishiqing.qywx.service.model.corp.CorpVO;

public interface RsqCorpService {
    public CorpVO pushAndCreateCorp(CorpVO corpVO) throws RsqSyncException;
    public void pushAndCreateCorpAll(CorpVO corpVO) throws RsqSyncException, HttpException, RsqUpdateNotExistsException;
}
