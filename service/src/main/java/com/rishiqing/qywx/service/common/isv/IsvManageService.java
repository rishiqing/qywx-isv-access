package com.rishiqing.qywx.service.common.isv;


import com.rishiqing.qywx.service.model.isv.IsvVO;

public interface IsvManageService {
    /**
     * 获取isv的基本
     * @param corpId
     * @return
     */
    IsvVO getIsv(String corpId);

    /**
     * 保存isv
     * @param isvVO
     */
    void saveIsv(IsvVO isvVO);
}
