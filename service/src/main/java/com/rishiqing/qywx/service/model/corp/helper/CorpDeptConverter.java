package com.rishiqing.qywx.service.model.corp.helper;

import com.rishiqing.qywx.dao.model.corp.CorpDeptDO;
import com.rishiqing.qywx.service.model.corp.CorpDeptVO;

public class CorpDeptConverter {
    public static CorpDeptVO corpDeptDO2CorpDeptVO(CorpDeptDO obj){
        if(obj == null){
            return null;
        }
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
        if(obj == null){
            return null;
        }
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

    /**
     * merge方法,将old merge到target对象,如果old的属性为null,则不进行合并
     * 注意:
     * 1  CorpDeptVO中的基本数据类型会发生覆盖
     * @param old
     * @param target
     * @return
     */
    public static void mergeCorpDeptVO(CorpDeptVO old, CorpDeptVO target){
        if(null != old.getName()){
            target.setName(old.getName());
        }
        if(null != old.getOrder()){
            target.setOrder(old.getOrder());
        }
        if(null != old.getParentId()){
            target.setParentId(old.getParentId());
        }
        if(null != old.getRsqId()){
            target.setRsqId(old.getRsqId());
        }
    }
}
