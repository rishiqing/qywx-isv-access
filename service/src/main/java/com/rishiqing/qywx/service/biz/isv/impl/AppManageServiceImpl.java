package com.rishiqing.qywx.service.biz.isv.impl;

import com.rishiqing.qywx.service.biz.isv.AppManageService;
import com.rishiqing.qywx.dao.mapper.isv.AppDao;
import com.rishiqing.qywx.service.model.isv.AppVO;
import com.rishiqing.qywx.service.model.isv.helper.AppConverter;
import com.rishiqing.qywx.dao.model.isv.AppDO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
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
