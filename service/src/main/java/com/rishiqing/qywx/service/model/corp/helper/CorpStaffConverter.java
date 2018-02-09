package com.rishiqing.qywx.service.model.corp.helper;

import com.alibaba.fastjson.JSONArray;
import com.rishiqing.common.model.RsqCommonUserVO;
import com.rishiqing.qywx.dao.model.corp.CorpStaffDO;
import com.rishiqing.qywx.service.model.corp.CorpDeptVO;
import com.rishiqing.qywx.service.model.corp.CorpStaffVO;
import com.rishiqing.qywx.service.model.corp.CorpVO;
import com.rishiqing.qywx.service.model.corp.LoginUserVO;

import java.util.ArrayList;
import java.util.List;

public class CorpStaffConverter {
    public static CorpStaffVO corpStaffDO2CorpStaffVO(CorpStaffDO obj){
        if(obj == null){
            return null;
        }
        CorpStaffVO newObj = new CorpStaffVO();
        newObj.setId(obj.getId());
        newObj.setCorpId(obj.getCorpId());
        newObj.setUserId(obj.getUserId());
        newObj.setName(obj.getName());
        newObj.setAvatar(obj.getAvatar());
        newObj.setDepartment(obj.getDepartment());
        newObj.setOrderInDepts(obj.getOrderInDepts());
        newObj.setIsLeaderInDepts(obj.getIsLeaderInDepts());
        newObj.setPosition(obj.getPosition());
        newObj.setMobile(obj.getMobile());
        newObj.setGender(obj.getGender());
        newObj.setEmail(obj.getEmail());
        newObj.setTel(obj.getTel());
        newObj.setEnglishName(obj.getEnglishName());
        newObj.setStatus(obj.getStatus());
        newObj.setExtattr(obj.getExtattr());
        newObj.setRsqUserId(obj.getRsqUserId());
        newObj.setRsqUsername(obj.getRsqUsername());
        newObj.setRsqPassword(obj.getRsqPassword());
        newObj.setRsqLoginToken(obj.getRsqLoginToken());
        newObj.setAdminType(obj.getAdminType());
        newObj.setUnionId(obj.getUnionId());
        return newObj;
    }

    public static CorpStaffDO corpStaffVO2CorpStaffDO(CorpStaffVO obj){
        if(obj == null){
            return null;
        }
        CorpStaffDO newObj = new CorpStaffDO();
        newObj.setId(obj.getId());
        newObj.setCorpId(obj.getCorpId());
        newObj.setUserId(obj.getUserId());
        newObj.setName(obj.getName());
        newObj.setAvatar(obj.getAvatar());
        newObj.setDepartment(obj.getDepartment());
        newObj.setOrderInDepts(obj.getOrderInDepts());
        newObj.setIsLeaderInDepts(obj.getIsLeaderInDepts());
        newObj.setPosition(obj.getPosition());
        newObj.setMobile(obj.getMobile());
        newObj.setGender(obj.getGender());
        newObj.setEmail(obj.getEmail());
        newObj.setTel(obj.getTel());
        newObj.setEnglishName(obj.getEnglishName());
        newObj.setStatus(obj.getStatus());
        newObj.setExtattr(obj.getExtattr());
        newObj.setRsqUserId(obj.getRsqUserId());
        newObj.setRsqUsername(obj.getRsqUsername());
        newObj.setRsqPassword(obj.getRsqPassword());
        newObj.setRsqLoginToken(obj.getRsqLoginToken());
        newObj.setAdminType(obj.getAdminType());
        newObj.setUnionId(obj.getUnionId());
        return newObj;
    }

    public static List<CorpStaffVO> corpStaffDOList2CorpStaffVOList(List<CorpStaffDO> corpStaffDOList){
        if(null == corpStaffDOList){
            return null;
        }
        List<CorpStaffVO> voList = new ArrayList<CorpStaffVO>(corpStaffDOList.size());
        for(CorpStaffDO corpStaffDO : corpStaffDOList){
            voList.add(corpStaffDO2CorpStaffVO(corpStaffDO));
        }
        return voList;
    }

    public static LoginUserVO corpStaffVO2LoginUserVO(CorpStaffVO obj){
        if(obj == null){
            return null;
        }
        LoginUserVO newObj = new LoginUserVO();
        newObj.setId(obj.getId());
        newObj.setUserId(obj.getUserId());
        return newObj;
    }

    public static RsqCommonUserVO corpStaffVO2RsqCommonUserVO(CorpVO corpVO, List<CorpDeptVO> corpDeptVOList, CorpStaffVO corpStaffVO){
        RsqCommonUserVO user = new RsqCommonUserVO();
        //  properties required
        user.setCorpId(corpStaffVO.getCorpId());
        user.setUserId(corpStaffVO.getUserId());

        if(null != corpStaffVO.getRsqUserId())
            user.setId(Long.valueOf(corpStaffVO.getRsqUserId()));
        if(null != corpStaffVO.getRsqUsername())
            user.setUsername(corpStaffVO.getRsqUsername());
        if(null != corpStaffVO.getRsqPassword())
            user.setPassword(corpStaffVO.getRsqPassword());
        if(null != corpStaffVO.getName())
            user.setRealName(corpStaffVO.getName());
        if(null != corpStaffVO.getUnionId())
            user.setUnionId(corpStaffVO.getUnionId());
        if(null != corpStaffVO.getAdminType())
            user.setAdmin(corpStaffVO.getAdminType() != -1);

        //  关联属性
        if(null != corpVO.getRsqId())
            user.setTeamId(Long.valueOf(corpVO.getRsqId()));
        if(null != corpDeptVOList && corpDeptVOList.size() > 0){
            JSONArray idArrays = new JSONArray();
            for(CorpDeptVO corpDept : corpDeptVOList){
                if(null != corpDept.getRsqId()){
                    idArrays.add(Long.valueOf(corpDept.getRsqId()));
                }
            }
            user.setDepartment(JSONArray.toJSONString(idArrays));
        }
        return user;
    }

    public static void mergeCorpStaffVO(CorpStaffVO old, CorpStaffVO target) {
        if(null != old.getName())
            target.setName(old.getName());
        if(null != old.getAvatar())
            target.setAvatar(old.getAvatar());
        if(null != old.getDepartment())
            target.setDepartment(old.getDepartment());
        if(null != old.getOrderInDepts())
            target.setOrderInDepts(old.getOrderInDepts());
        if(null != old.getIsLeaderInDepts())
            target.setIsLeaderInDepts(old.getIsLeaderInDepts());
        if(null != old.getPosition())
            target.setPosition(old.getPosition());
        if(null != old.getMobile())
            target.setMobile(old.getMobile());
        if(null != old.getGender())
            target.setGender(old.getGender());
        if(null != old.getEmail())
            target.setEmail(old.getEmail());
        if(null != old.getTel())
            target.setTel(old.getTel());
        if(null != old.getEnglishName())
            target.setEnglishName(old.getEnglishName());
        if(null != old.getStatus())
            target.setStatus(old.getStatus());
        if(null != old.getExtattr())
            target.setExtattr(old.getExtattr());
        if(null != old.getRsqUserId())
            target.setRsqUserId(old.getRsqUserId());
        if(null != old.getRsqUsername())
            target.setRsqUsername(old.getRsqUsername());
        if(null != old.getRsqPassword())
            target.setRsqPassword(old.getRsqPassword());
        if(null != old.getRsqLoginToken())
            target.setRsqLoginToken(old.getRsqLoginToken());
        if(null != old.getAdminType())
            target.setAdminType(old.getAdminType());
    }
}
