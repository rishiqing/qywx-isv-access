package com.rishiqing.qywx.service.constant;

/**
 * 在异步过程中发生失败的类型
 * @author Wallace Mao
 * Date: 2018-02-28 14:57
 */
public enum CallbackFailType {
    //  授权回调过程中发生失败
    //  https://work.weixin.qq.com/api/doc#10982
    /**
     * 授权过程目前只处理开通应用，即create_auth消息
     * 开通应用动作分为三步，分别异步执行:
     * 1 接受到回调消息，并发送请求获取permanentCode
     * 2 根据permanentCode发送请求抓取企业corp/dept/staff/admin等信息，并保存到本地
     * 3 企业的dept/staff/admin推送到日事清保存
     * 以下两种fail类型分别对应2 3三个阶段发生失败
     * 第一阶段发生失败将直接返回给用户开通失败
     */
    AUTH_CALLBACK_FAIL_SAVE_NEW_CORP("auth_callback_fail_save_new_corp"),
    AUTH_CALLBACK_FAIL_PUSH_NEW_CORP("auth_callback_fail_push_new_corp"),


    //  通讯论回调失败
    //  https://work.weixin.qq.com/api/doc#10982
    /**
     * 普通通讯录回调消息处理过程分为两步:
     * 1 接受回调消息，并保存更新本地信息
     * 2 将回调消息推送到日事清
     * 以下两种fail类型分别对应1 2两个阶段发生的失败
     */
    CONTACT_CALLBACK_FAIL_SAVE_COMMON("contact_callback_fail_save_common"),
    CONTACT_CALLBACK_FAIL_PUSH_COMMON("contact_callback_fail_push_common");

    private final String key;

    CallbackFailType(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public static CallbackFailType getCallbackFailType(String key){
        CallbackFailType[] arr = CallbackFailType.values();
        for(CallbackFailType type : arr){
            if(type.getKey().equalsIgnoreCase(key)){
                return type;
            }
        }
        return null;
    }
}
