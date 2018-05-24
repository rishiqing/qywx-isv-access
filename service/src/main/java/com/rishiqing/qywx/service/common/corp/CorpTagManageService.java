package com.rishiqing.qywx.service.common.corp;

import com.rishiqing.qywx.service.model.corp.CorpTagDetailVO;

/**
 * @author Wallace Mao
 * Date: 2018-04-27 20:37
 */
public interface CorpTagManageService {

    void saveOrUpdateCorpTag(CorpTagDetailVO corpTagDetailVO);

    void saveOrUpdateCorpTagCorpStaff(CorpTagDetailVO corpTagDetailVO);

    void saveOrUpdateCorpTagCorpDept(CorpTagDetailVO corpTagDetailVO);
}
