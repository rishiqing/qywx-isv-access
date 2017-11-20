package com.rishiqing.qywx.service.model.corp.helper;

import com.rishiqing.qywx.dao.model.corp.CorpStaffIdsDO;
import com.rishiqing.qywx.service.model.corp.CorpStaffIdsVO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: user 毛文强
 * Date: 2017/11/20
 * Time: 14:28
 * To change this template use File | Settings | File Templates.
 */
public class CorpStaffIdsConverter {
    public static CorpStaffIdsVO CorpStaffIdsDO2CorpStaffIdsVO(CorpStaffIdsDO obj){
        if(obj == null){
            return null;
        }
        CorpStaffIdsVO newObj = new CorpStaffIdsVO();
        newObj.setUserId(obj.getUserId());
        newObj.setRsqUserId(obj.getRsqUserId());
        newObj.setName(obj.getName());
        newObj.setAvatar(obj.getAvatar());
        return newObj;
    }
    public static List<CorpStaffIdsVO> CorpStaffIdsDOList2CorpStaffIdsVOList(List<CorpStaffIdsDO> list){
        if(list == null){
            return null;
        }
        List<CorpStaffIdsVO> newList = new ArrayList<>(list.size());
        Iterator<CorpStaffIdsDO> it = list.iterator();
        while (it.hasNext()){
            CorpStaffIdsDO obj = it.next();
            newList.add(CorpStaffIdsDO2CorpStaffIdsVO(obj));
        }
        return newList;
    }
}
