package com.jk.kq.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jk.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 核销申请实体类
 * @author 何任鹏
 * @version 1.0
 * @date 2020年06月01日
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("jk_writeoff_record")
public class WriteoffRecord extends BaseEntity {

    /**
     * user_id
     */
    private Integer userId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 公司ID/部门ID
     */
    private Integer organId;

    /**
     * 公司/部门
     */
    private String organ;

    /**
     * 核销日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    /**
     * 核销原因
     */
    private String reason;


    /**
     * 核销类型
     */
    private Integer typeId;

    /**
     * 申请人
     */
    private String applyUser;

    /**
     * 申请时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date applyTime;


    /**
     * 流程实例ID
     */
    private String instanceId;

    /**
     * 创建者
     */
    private String createBy;


    private String title;


    /** 申请人姓名 */
    @TableField(exist=false)
    private String applyUserName;

    /** 任务ID */
    @TableField(exist=false)
    private String taskId;

    /** 任务名称 */
    @TableField(exist=false)
    private String taskName;

    /** 办理时间 */
    @TableField(exist=false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date doneTime;

    /** 创建人 */
    @TableField(exist=false)
    private String createUserName;



    /** 流程实例状态 1 激活 2 挂起 */
    @TableField(exist=false)
    private String suspendState;

    private Integer isEnd;

    private Integer isPass;

}