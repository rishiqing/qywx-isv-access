package com.rishiqing.qywx.service.common.isv.impl;

import com.rishiqing.qywx.dao.mapper.isv.IsvDao;
import com.rishiqing.qywx.service.common.isv.IsvManageService;
import com.rishiqing.qywx.service.model.isv.IsvVO;
import com.rishiqing.qywx.service.model.isv.helper.IsvConverter;
import org.springframework.beans.factory.annotation.Autowired;

public class IsvManageServiceImpl implements IsvManageService {
    @Autowired
    private IsvDao isvDao;

    @Override
    public IsvVO getIsv(String corpId) {
        return IsvConverter.isvDO2IsvVO(
                isvDao.getIsvByCorpId(corpId)
        );
    }

    @Override
    public void saveIsv(IsvVO isvVO) {
        isvDao.saveOrUpdateIsv(
                IsvConverter.isvVO2IsvDO(isvVO)
        );
    }
}
