package com.rishiqing.qywx.dao.mapper.isv;

import com.rishiqing.qywx.dao.model.isv.AppDO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("appDao")
public interface AppDao {
    /**
     * 获取所有的
     * @return
     */
    List<AppDO> getAllApp();
}
