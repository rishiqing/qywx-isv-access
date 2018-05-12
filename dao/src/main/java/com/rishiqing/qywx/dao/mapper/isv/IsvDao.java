package com.rishiqing.qywx.dao.mapper.isv;

import com.rishiqing.qywx.dao.model.isv.IsvDO;
import com.rishiqing.qywx.dao.model.isv.SuiteDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("isvDao")
public interface IsvDao {
    /**
     * 根据corpId获取isv
     * @return
     */
    IsvDO getIsvByCorpId(@Param("corpId") String corpId);

    /**
     * 保存或者更新isv，如果isv的corpId值不存在，那么走insert，如果存在，走update
     * @param isvDO
     */
    void saveOrUpdateIsv(IsvDO isvDO);

    /**
     * 根据suite的key值删除suite
     * @param isvDO
     */
    void removeIsv(IsvDO isvDO);
}
