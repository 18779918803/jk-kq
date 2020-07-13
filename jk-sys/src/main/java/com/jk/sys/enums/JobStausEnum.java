package com.jk.sys.enums;

/**
 * 任务执行状态枚举
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2019年12月25日
 */
public enum JobStausEnum {
    /**
     * 启动任务
     */
    START("0"),
    /**
     * 停止任务
     */
    STOP("1");

    JobStausEnum(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }
}
