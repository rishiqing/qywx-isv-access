package com.rishiqing.qywx.service.biz.rsq;

import com.rishiqing.qywx.service.model.corp.CorpVO;

public interface RsqCorpService {
    public CorpVO pushAndCreateCorp(CorpVO corpVO);
    public void pushAndCreateCorpAll(CorpVO corpVO);
}
