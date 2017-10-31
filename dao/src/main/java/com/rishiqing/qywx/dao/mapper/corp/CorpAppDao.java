package com.rishiqing.qywx.dao.mapper.corp;

import com.rishiqing.qywx.dao.model.corp.CorpAppDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("corpAppDao")
public interface CorpAppDao {
    /**
     * 根据appId和corpId获取CorpApp
     * @param appId
     * @param corpId
     * @return
     */
    CorpAppDO getCorpAppByAppIdAndCorpId(@Param("appId") Long appId, @Param("corpId") String corpId);

    /**
     * 保存或更新corp
     * @param corpAppDO
     */
    void saveOrUpdateCorpApp(CorpAppDO corpAppDO);

    /**
     * 删除
     * @param appId
     * @param corpId
     */
    void removeCorpAppByAppIdAndCorpId(@Param("appId") Long appId, @Param("corpId") String corpId);
}
