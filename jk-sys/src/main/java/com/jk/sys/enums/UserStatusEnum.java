package com.jk.sys.enums;

/**
 * 用户账号状态枚举
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2019年12月25日
 */
public enum UserStatusEnum {
    /**
     * 正常的
     */
    NORMAL("0"),
    /**
     * 禁用的
     */
    FORBIDDEN("1");

    UserStatusEnum(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }
}
