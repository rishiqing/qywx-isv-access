package com.rishiqing.qywx.dao.mapper.message;

import com.rishiqing.qywx.dao.model.message.IsvMessage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Wallace Mao
 * Date: 2018-05-30 18:11
 */
@Repository("isvMessageDao")
public interface IsvMessageDao {
    IsvMessage getIsvMessageByKey(@Param("messageKey") String messageKey);
}
