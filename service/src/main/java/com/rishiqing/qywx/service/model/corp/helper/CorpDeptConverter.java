package com.rishiqing.qywx.service.model.corp.helper;

import com.rishiqing.common.model.RsqDepartmentVO;
import com.rishiqing.qywx.dao.model.corp.CorpDeptDO;
import com.rishiqing.qywx.service.model.corp.CorpDeptVO;
import com.rishiqing.qywx.service.model.corp.CorpVO;

import java.util.ArrayList;
import java.util.List;

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

    public static List<CorpDeptVO> corpDeptDOList2CorpDeptVOList(List<CorpDeptDO> doList){
        if(null == doList){
            return null;
        }
        List<CorpDeptVO> voList = new ArrayList<CorpDeptVO>(doList.size());
        for(CorpDeptDO corpDeptDO : doList){
            voList.add(corpDeptDO2CorpDeptVO(corpDeptDO));
        }
        return voList;
    }

    public static RsqDepartmentVO corpDeptVO2RsqDepartment(CorpVO corpVO, CorpDeptVO parentCorpDeptVO, CorpDeptVO corpDeptVO){
        RsqDepartmentVO rsqDepartmentVO = new RsqDepartmentVO();
        //  properties required
        rsqDepartmentVO.setCorpId(corpDeptVO.getCorpId());
        rsqDepartmentVO.setDeptId(String.valueOf(corpDeptVO.getDeptId()));

        //  properties optional (can be null)
        rsqDepartmentVO.setName(corpDeptVO.getName());

        //TODO  由于日事清后台order字段为int类型，企业微信为long类型，为防止溢出，暂时这么做，等日事清后台更新了之后需要改过来
        //TODO  By Wallace Mao
        long orderNum = corpDeptVO.getOrder();
        if( orderNum > Integer.MAX_VALUE){
            orderNum = Integer.MAX_VALUE;
        }
        rsqDepartmentVO.setOrderNum(orderNum);

        rsqDepartmentVO.setTeamId(corpVO.getRsqId());
        if(null != corpDeptVO.getRsqId()){
            rsqDepartmentVO.setId(Long.valueOf(corpDeptVO.getRsqId()));
        }
        if(null != parentCorpDeptVO){
            rsqDepartmentVO.setParentId(parentCorpDeptVO.getRsqId());
        }
        return rsqDepartmentVO;
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
