package com.jk.kq.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jk.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 打卡记录实体类
 *
 * @author 何任鹏
 * @version 1.0
 * @date 2020年05月25日
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("jk_clock")
public class Clock extends BaseEntity {

    /**
     * 姓名
     */
    private Integer openUserId;

    /**
     * 设备
     */
    private Integer deviceId;

    /**
     * 打卡时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;

    private Integer way;

    private Integer type;

    /**
     * 打卡状态0正常1迟到2旷工3早退4加班
     */
    private Integer state;

}