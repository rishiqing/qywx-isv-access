package com.rishiqing.qywx.dao.mapper.fail;

import com.rishiqing.qywx.dao.model.fail.FailContactCallbackDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2018-02-28 15:45
 */
@Repository("failContactCallbackDao")
public interface FailContactCallbackDao {
    /**
     * 保存或者更新ffailContactCallbackDO
     * @param failContactCallbackDO
     */
    void saveOrUpdateFailContactCallback(FailContactCallbackDO failContactCallbackDO);
    /**
     * 根据id删除failContactCallbackDO
     * @param id
     */
    void removeFailContactCallbackById(@Param("id") Long id);

    /**
     * 查找指定数量的记录
     * @param limit
     * @return
     */
    List<FailContactCallbackDO> listFailContactCallbackWithLimit(@Param("limit") Long limit);
}
