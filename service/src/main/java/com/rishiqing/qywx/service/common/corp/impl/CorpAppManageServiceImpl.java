package com.rishiqing.qywx.service.common.corp.impl;

import com.rishiqing.qywx.dao.mapper.corp.CorpAppDao;
import com.rishiqing.qywx.dao.model.corp.CorpAppDO;
import com.rishiqing.qywx.service.common.corp.CorpAppManageService;
import com.rishiqing.qywx.service.model.corp.CorpAppVO;
import com.rishiqing.qywx.service.model.corp.helper.CorpAppConverter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class CorpAppManageServiceImpl implements CorpAppManageService {
    @Autowired
    private CorpAppDao corpAppDao;
    @Override
    public CorpAppVO getCorpApp(Long appId, String corpId) {
        return CorpAppConverter.corpAppDO2CorpAppVO(
                corpAppDao.getCorpAppByAppIdAndCorpId(appId, corpId)
        );
    }

    @Override
    public List<CorpAppVO> listCorpApp(String corpId) {
        List<CorpAppDO> list = corpAppDao.listCorpAppByCorpId(corpId);
        List<CorpAppVO> voList = new ArrayList<>();
        for(CorpAppDO corpAppDO : list){
            voList.add(CorpAppConverter.corpAppDO2CorpAppVO(corpAppDO));
        }
        return voList;
    }

    @Override
    public void saveCorpApp(CorpAppVO corpAppVO) {
        corpAppDao.saveOrUpdateCorpApp(
                CorpAppConverter.corpAppVO2CorpAppDO(corpAppVO)
        );
    }

    @Override
    public void removeCorpApp(Long appId, String corpId) {
        corpAppDao.removeCorpAppByAppIdAndCorpId(appId, corpId);
    }
}
