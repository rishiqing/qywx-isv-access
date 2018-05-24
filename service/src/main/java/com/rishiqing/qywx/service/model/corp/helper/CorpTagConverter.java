package com.rishiqing.qywx.service.model.corp.helper;

import com.rishiqing.qywx.dao.model.corp.CorpTagCorpDeptDO;
import com.rishiqing.qywx.dao.model.corp.CorpTagCorpStaffDO;
import com.rishiqing.qywx.dao.model.corp.CorpTagDO;
import com.rishiqing.qywx.service.model.corp.CorpTagDetailVO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2018-04-28 11:15
 */
public class CorpTagConverter {
    public static CorpTagDO corpTagDetailVO2CorpTagDO(CorpTagDetailVO obj){
        if(obj == null){
            return null;
        }
        CorpTagDO corpTagDO = new CorpTagDO();
        corpTagDO.setCorpId(obj.getCorpId());
        corpTagDO.setTagId(obj.getTagId());
        corpTagDO.setTagName(obj.getTagName());

        return corpTagDO;
    }

    public static List<CorpTagCorpStaffDO> corpTagDetailVO2CorpTagCorpStaffDOList(CorpTagDetailVO obj){
        if(obj == null){
            return null;
        }
        List<String> list = obj.getUserList();
        List<CorpTagCorpStaffDO> staffList = new ArrayList<>(list.size());
        for(String userId : list){
            CorpTagCorpStaffDO staffDO = new CorpTagCorpStaffDO();
            staffDO.setCorpId(obj.getCorpId());
            staffDO.setTagId(obj.getTagId());
            staffDO.setUserId(userId);
            staffList.add(staffDO);
        }

        return staffList;
    }

    public static List<CorpTagCorpDeptDO> corpTagDetailVO2CorpTagCorpDeptDOList(CorpTagDetailVO obj){
        if(obj == null){
            return null;
        }

        List<Long> list = obj.getPartyList();
        List<CorpTagCorpDeptDO> deptList = new ArrayList<>(list.size());
        for(Long deptId : list){
            CorpTagCorpDeptDO deptDO = new CorpTagCorpDeptDO();
            deptDO.setCorpId(obj.getCorpId());
            deptDO.setTagId(obj.getTagId());
            deptDO.setDeptId(deptId);
            deptList.add(deptDO);
        }

        return deptList;
    }
}
