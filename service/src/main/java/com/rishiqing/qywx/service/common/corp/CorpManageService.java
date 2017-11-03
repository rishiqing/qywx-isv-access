package com.rishiqing.qywx.service.common.corp;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.rishiqing.qywx.service.exception.HttpException;
import com.rishiqing.qywx.service.exception.SuiteAccessTokenExpiredException;
import com.rishiqing.qywx.service.model.corp.CorpSuiteVO;
import com.rishiqing.qywx.service.model.corp.CorpVO;
import com.rishiqing.qywx.service.model.isv.SuiteTokenVO;

public interface CorpManageService {
    /**
     * 保存corp信息
     * @param corpVO
     */
    void saveOrUpdateCorp(CorpVO corpVO);

    /**
     * 标记删除公司
     * @param corpId
     * @param authCanceled
     */
    void markRemoveCorp(String corpId, Boolean authCanceled);
}
