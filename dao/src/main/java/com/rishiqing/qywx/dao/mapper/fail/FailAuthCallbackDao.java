package com.rishiqing.qywx.dao.mapper.fail;

import com.rishiqing.qywx.dao.model.fail.FailAuthCallbackDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    /**
     * 查找指定数量的记录
     * @param limit
     * @return
     */
    List<FailAuthCallbackDO> listFailAuthCallbackWithLimit(@Param("limit") Long limit);
}
