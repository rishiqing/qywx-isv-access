package com.rishiqing.qywx.service.constant;

/**
 * @author Wallace Mao
 * Date: 2018-02-10 14:58
 */
public enum CallbackChangeType {
    //  新建成员
    CREATE_USER("create_user"),
    //  更新成员
    UPDATE_USER("update_user"),
    //  删除成员
    DELETE_USER("delete_user"),
    //  创建部门
    CREATE_PARTY("create_party"),
    //  更新部门
    UPDATE_PARTY("update_party"),
    //  删除部门
    DELETE_PARTY("delete_party"),
    //  更新标签
    UPDATE_TAG("update_tag");

    private final String key;

    CallbackChangeType(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
