package com.rishiqing.qywx.service.model.corp.helper;

import com.rishiqing.qywx.dao.model.corp.CorpDeptDO;
import com.rishiqing.qywx.service.model.corp.CorpDeptVO;

public class CorpDeptConverter {
    public static CorpDeptVO corpDeptDO2CorpDeptVO(CorpDeptDO obj){
        CorpDeptVO newObj = new CorpDeptVO();
        newObj.setId(obj.getId());
        newObj.setCorpId(obj.getCorpId());
        newObj.setDeptId(obj.getDeptId());
        newObj.setName(obj.getName());
        newObj.setOrder(obj.getOrder());
        newObj.setParentId(obj.getParentId());
        newObj.setRsqId(obj.getRsqId());
        return newObj;
    }

    public static CorpDeptDO corpDeptVO2CorpDeptDO(CorpDeptVO obj){
        CorpDeptDO newObj = new CorpDeptDO();
        newObj.setId(obj.getId());
        newObj.setCorpId(obj.getCorpId());
        newObj.setDeptId(obj.getDeptId());
        newObj.setName(obj.getName());
        newObj.setOrder(obj.getOrder());
        newObj.setParentId(obj.getParentId());
        newObj.setRsqId(obj.getRsqId());
        return newObj;
    }
}
