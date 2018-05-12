package com.rishiqing.qywx.service.constant;

/**
 * @author Wallace Mao
 * Date: 2018-02-10 14:58
 */
public enum CallbackInfoType {
    //  ticket推送
    SUITE_TICKET("suite_ticket"),
    //  授权成功
    CREATE_AUTH("create_auth"),
    //  授权变更
    CHANGE_AUTH("change_auth"),
    //  授权取消
    CANCEL_AUTH("cancel_auth"),
    //  通讯录变更
    CHANGE_CONTACT("change_contact"),
    //  定制化注册成功
    REGISTER_CORP("register_corp");

    private final String key;

    CallbackInfoType(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public static CallbackInfoType getCallbackInfoType(String key){
        CallbackInfoType[] arr = CallbackInfoType.values();
        for(CallbackInfoType type : arr){
            if(type.getKey().equalsIgnoreCase(key)){
                return type;
            }
        }
        return null;
    }
}
