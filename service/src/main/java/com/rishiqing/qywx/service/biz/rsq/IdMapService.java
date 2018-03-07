package com.rishiqing.qywx.service.biz.rsq;

import com.rishiqing.qywx.service.model.corp.CorpStaffIdsVO;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: user 毛文强
 * Date: 2017/11/20
 * Time: 14:36
 * To change this template use File | Settings | File Templates.
 */
public interface IdMapService {
    /**
     * 根据userId获取rsqId
     * @param corpId
     * @param userIds
     * @return
     */
    List<CorpStaffIdsVO> getRsqIdFromUserId(String corpId, String[] userIds);

    /**
     * 根据rsqId获取userId
     * @param corpId
     * @param rsqIds
     * @return
     */
    List<CorpStaffIdsVO> getUserIdFromRsqId(String corpId, String[] rsqIds);
}
