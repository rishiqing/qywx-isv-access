package com.rishiqing.qywx.service.biz.corp;

import com.rishiqing.qywx.service.model.corp.CorpTokenVO;

/**
 * @author Wallace Mao
 * Date: 2018-04-27 21:56
 */
public interface TagService {
    void fetchAndSaveTagDetailList(CorpTokenVO corpTokenVO, Long tagId);
}
