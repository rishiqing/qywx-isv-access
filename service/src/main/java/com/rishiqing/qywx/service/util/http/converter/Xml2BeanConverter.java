package com.rishiqing.qywx.service.util.http.converter;

import com.rishiqing.qywx.service.model.corp.CorpDeptVO;
import com.rishiqing.qywx.service.model.corp.CorpStaffVO;

import java.util.Map;

/**
 * @author Wallace Mao
 * Date: 2018-02-07 17:16
 */
public class Xml2BeanConverter {
    /**
     * @param map
     * @return
     */
    public static CorpDeptVO generateCorpDept(Map map){
        CorpDeptVO deptVO = new CorpDeptVO();
        deptVO.setCorpId((String)map.get("AuthCorpId"));
        deptVO.setDeptId(Long.valueOf((String)map.get("Id")));
        if(map.containsKey("Name")){
            deptVO.setName((String)map.get("Name"));
        }
        if(map.containsKey("ParentId")){
            deptVO.setParentId(Long.valueOf((String)map.get("ParentId")));
        }
        if(map.containsKey("Order")){
            deptVO.setOrder(Long.valueOf((String)map.get("Order")));
        }
        return deptVO;
    }

    /**
     *
     * @param map
     * @return
     */
    public static CorpStaffVO generateCorpStaff(Map map){
        CorpStaffVO staffVO = new CorpStaffVO();
        staffVO.setCorpId((String)map.get("AuthCorpId"));
        staffVO.setUserId((String)map.get("UserID"));
        if(map.containsKey("Name"))
            staffVO.setName((String)map.get("Name"));
        if(map.containsKey("Department"))
            staffVO.setDepartment((String)map.get("Department"));
        if(map.containsKey("Mobile"))
            staffVO.setMobile((String)map.get("Mobile"));
        if(map.containsKey("Position"))
            staffVO.setPosition((String)map.get("Position"));
        if(map.containsKey("Gender"))
            staffVO.setGender((String)map.get("Gender"));
        if(map.containsKey("Email"))
            staffVO.setEmail((String)map.get("Email"));
        if(map.containsKey("Avatar"))
            staffVO.setAvatar((String)map.get("Avatar"));
        if(map.containsKey("EnglishName"))
            staffVO.setEnglishName((String)map.get("EnglishName"));
        if(map.containsKey("IsLeader"))
            staffVO.setIsLeaderInDepts((String)map.get("IsLeader"));
        if(map.containsKey("Telephone"))
            staffVO.setTel((String)map.get("Telephone"));
        if(map.containsKey("ExtAttr"))
            staffVO.setExtattr((String)map.get("ExtAttr"));
        if(map.containsKey("Status"))
            staffVO.setStatus(Long.valueOf((String)map.get("Status")));

        return staffVO;
    }
}
