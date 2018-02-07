package com.rishiqing.qywx.service.model.corp.helper;

import com.rishiqing.qywx.dao.model.corp.CorpStaffDO;
import com.rishiqing.qywx.service.model.corp.CorpStaffVO;
import com.rishiqing.qywx.service.model.corp.LoginUserVO;

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
        return newObj;
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
    }
}
