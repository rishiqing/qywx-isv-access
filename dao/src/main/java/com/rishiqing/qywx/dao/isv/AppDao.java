package com.rishiqing.qywx.dao.isv;

import com.rishiqing.qywx.model.isv.AppDO;
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
