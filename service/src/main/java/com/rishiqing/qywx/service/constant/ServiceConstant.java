package com.rishiqing.qywx.service.constant;

/**
 * 系统常量
 */
public class ServiceConstant {
    public static final Long CORP_APP_MAX_LIMIT = 10L;
    public static final Long CORP_DEPT_WARN_LIMIT = 100L;  //  企业的部门数量报警的门槛值

    //  回调消息的最大程度，与数据库中保持一致。
    //  为保证数据库保存不出错，当超出该值时，数据库保存的消息内容会被截取
    public static final Long CALLBACK_EVENT_CONTENT_MAX_SIZE = 512L;
    public static final Long FAIL_AUTH_CALLBACK_LIST_LIMIT = 5L;
    public static final Long FAIL_CONTACT_CALLBACK_LIST_LIMIT = 10L;
}
