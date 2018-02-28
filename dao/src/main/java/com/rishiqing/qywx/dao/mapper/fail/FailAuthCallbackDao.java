package com.rishiqing.qywx.dao.mapper.fail;

import com.rishiqing.qywx.dao.model.fail.FailAuthCallbackDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Wallace Mao
 * Date: 2018-02-28 15:45
 */
@Repository("failAuthCallbackDao")
public interface FailAuthCallbackDao {
    /**
     * 保存或者更新failAuthCallbackDO
     * @param failAuthCallbackDO
     */
    void saveOrUpdateFailAuthCallback(FailAuthCallbackDO failAuthCallbackDO);
    /**
     * 根据id删除failAuthCallbackDO
     * @param id
     */
    void removeFailAuthCallbackById(@Param("id") Long id);
}
