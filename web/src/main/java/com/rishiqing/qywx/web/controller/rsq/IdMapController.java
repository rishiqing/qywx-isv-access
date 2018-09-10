package com.rishiqing.qywx.web.controller.rsq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rishiqing.qywx.service.biz.rsq.IdMapService;
import com.rishiqing.qywx.service.model.corp.CorpStaffIdsVO;
import com.rishiqing.qywx.web.constant.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Wallace on 2017/1/13.
 */
@Controller
public class IdMapController {
    private static final Logger logger = LoggerFactory.getLogger("WEB_ID_MAP_LOGGER");

    @Autowired
    private IdMapService idMapService;

    @ResponseBody
    @RequestMapping(value = "/idmap/userid2rsqid", method = {RequestMethod.POST})
    public Map userId2RsqId(
            @RequestParam("corpid") String corpId,
            @RequestBody String jsonBody
    ) {
        logger.info("--userId2RsqId--, corpid: {}, body: {}", corpId, jsonBody);
        Map<String, Object> map = new HashMap<String, Object>();
        try{
            JSONArray arr = JSON.parseArray(jsonBody);
            List<CorpStaffIdsVO> list = idMapService.getRsqIdFromUserId(corpId, arr.toArray(new String[]{}));
            map.put("result", list);
            map.put("errcode", ResultCode.NO_ERROR);
            map.put("errmsg", ResultCode.NO_ERROR_MSG);
        }catch(Exception e){
            logger.error("internal error: " + corpId + ", body: " + jsonBody, e);
            map.put("errcode", ResultCode.SYS_ERROR);
            map.put("errmsg", ResultCode.SYS_ERROR_MSG);
        }
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/idmap/rsqid2userid", method = {RequestMethod.POST})
    public Map rsqId2UserId(
            @RequestParam("corpid") String corpId,
            @RequestBody String jsonBody
    ) {
        logger.info("--rsqid2userid--, corpid: {}, body: {}", corpId, jsonBody);
        Map<String, Object> map = new HashMap<String, Object>();
        try{
            JSONArray arr = JSON.parseArray(jsonBody);
            Object[] objArray = arr.toArray();
            String[] idArray = new String[objArray.length];
            for(int i=0;i<objArray.length;i++){
                idArray[i] = String.valueOf(objArray[i]);
            }
            List<CorpStaffIdsVO> list = idMapService.getUserIdFromRsqId(corpId, idArray);
            map.put("result", list);
            map.put("errcode", ResultCode.NO_ERROR);
            map.put("errmsg", ResultCode.NO_ERROR_MSG);

        }catch(Exception e){
            logger.error("internal error: " + corpId + ", body: " + jsonBody, e);
            map.put("errcode", ResultCode.SYS_ERROR);
            map.put("errmsg", ResultCode.SYS_ERROR_MSG);
        }
        return map;
    }
}
