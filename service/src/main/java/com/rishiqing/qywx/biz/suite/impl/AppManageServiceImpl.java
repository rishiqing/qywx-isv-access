package com.rishiqing.qywx.biz.suite.impl;

import com.rishiqing.qywx.biz.suite.AppManageService;
import com.rishiqing.qywx.dao.isv.AppDao;
import com.rishiqing.qywx.model.AppVO;
import com.rishiqing.qywx.model.helper.AppConverter;
import com.rishiqing.qywx.model.isv.AppDO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AppManageServiceImpl implements AppManageService {
    @Autowired
    private AppDao appDao;

    public List<AppVO> listAllApp() {
        List<AppDO> appDOList = appDao.getAllApp();
        List<AppVO> appVOList = new ArrayList<AppVO>(appDOList.size());

        for (AppDO appDO : appDOList) {
            appVOList.add(AppConverter.appDO2AppVO(appDO));
        }
        return appVOList;
    }
}
