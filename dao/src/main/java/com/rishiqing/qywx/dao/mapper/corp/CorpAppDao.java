package com.rishiqing.qywx.dao.mapper.corp;

import com.rishiqing.qywx.dao.model.corp.CorpAppDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("corpAppDao")
public interface CorpAppDao {

    /**
     * 根据suiteKey和corpId获取CorpApp
     * @param suiteKey
     * @param corpId
     * @return
     */
    CorpAppDO getCorpAppBySuiteKeyAndCorpId(@Param("suiteKey") String suiteKey, @Param("corpId") String corpId);
    /**
     * 根据appId和corpId获取CorpApp，由于企业微信一个suite只能有一个app，且新接口中不会返回appid，所以该方法在新app中废弃
     * @param appId
     * @param corpId
     * @return
     */
    @Deprecated
    CorpAppDO getCorpAppByAppIdAndCorpId(@Param("appId") Long appId, @Param("corpId") String corpId);

    /**
     * 列出指定corpId下的所有corpApp
     * @param corpId
     * @return
     */
    List<CorpAppDO> listCorpAppByCorpId(@Param("corpId") String corpId);

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
