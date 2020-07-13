package com.jk.kq.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jk.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 设备实体类
 *
 * @author 何任鹏
 * @version 1.0
 * @date 2020年05月25日
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("jk_device_info")
public class DeviceInfo extends BaseEntity {

    /**
     * 设备信息
     */
    private String data;

    /**
     * 设备型号
     */
    private String model;

    /**
     * 系统版本号
     */
    private String rom;

    /**
     * SD卡剩余空间
     */
    private Integer space;

    /**
     * 应用版本号
     */
    private String app;

    /**
     * 剩余内存
     */
    private Integer memory;

    /**
     * 用户数
     */
    private Integer user;

    /**
     * 指纹数
     */
    private Integer fingerprint;

    /**
     * 人脸数
     */
    private Integer face;

    /**
     * 头像数量
     */
    private Integer headpic;

    /**
     * 打卡记录数量
     */
    private Integer clockin;

    /**
     * 现场照片数量
     */
    private Integer pic;

}