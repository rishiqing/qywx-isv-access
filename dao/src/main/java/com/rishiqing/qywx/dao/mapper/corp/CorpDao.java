package com.rishiqing.qywx.dao.mapper.corp;

import com.rishiqing.qywx.dao.model.corp.CorpDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("corpDao")
public interface CorpDao {
    /**
     * 根据corpId获取corp
     * @param corpId
     * @return
     */
    CorpDO getCorpByCorpId(@Param("corpId") String corpId);

    /**
     * 保存或者更新corp
     * @param corpDO
     */
    void saveOrUpdateCorp(CorpDO corpDO);

    /**
     * 根据corpId删除corp
     * @param corpId
     */
    void removeCorpByCorpId(@Param("corpId") String corpId);

    /**
     * 更新authCanceled字段
     * @param authCanceled
     */
    void updateCorpSetAuthCanceled(@Param("corpId") String corpId, @Param("isAuthCanceled") Boolean authCanceled);
}
