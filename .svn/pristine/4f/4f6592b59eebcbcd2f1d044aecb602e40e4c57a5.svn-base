package com.jk.db.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jk.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 督查督办延期（撤销）申请表实体类
 *
 * @author 刘杭
 * @version 1.0
 * @date 2020年06月10日
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("db_delay")
public class DbDelay extends BaseEntity {

    /**
     * 申请类别
     */
    private String applyType;

    /**
     * 所涉编号
     */
    private Integer dbId;

    /**
     * 督办事项来源
     */
    private String dbSource;

    /**
     * 来文日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dbLwDate;

    /**
     * 事项内容及要求（简要）
     */
    private String dbContentBrief;

    /**
     * 所涉事项要求办结期限
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dbAskFinaltime;

    /**
     * 重要程度（A：非常重要,B：重要,C：一般）
     */
    private String dbLevel;

    /**
     * 牵头部门（公司）
     */
    private String leadDepartment;

    /**
     * 流程实例ID
     */
    private String instanceId;

    /**
     * 责任部门（公司）
     */
    private String responsibilityDepartment;
    private Integer leadDepartmentId;

    private  Integer  responsibilityDepartmentId;


    /**
     * 申请原因
     */
    private String applyReason;

    /**
     * 调整后的时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date upTime;

    /**
     * 牵头单位负责人
     */
    private String unitPrincipal;

    /**
     * 牵头单位分管领导
     */
    private String unitLead;

    /**
     * 总经理意见
     */
    private String managerAdvice;

    /**
     * 董事长意见
     */
    private String presidentAdvice;


    @TableField(exist = false)
    /** 任务ID */
    private String taskId;

    @TableField(exist = false)
    /** 任务名称 */
    private String taskName;

    @TableField(exist = false)
    /** 办理时间 */
    private Date doneTime;

    @TableField(exist = false)
    /** 创建人 */
    private String createUserName;

    @TableField(exist = false)
    /** 流程实例状态 1 激活 2 挂起 */
    private String suspendState;

    /**
     * 更新者
     */
    private String updateBy;

    /** 申请人姓名 */
    @TableField(exist=false)
    private String applyUserName;

    /**
     * 申请人
     */
    private String applyUser;

    /**
     * 创建者
     */
    private String createBy;

    private Integer pid;

    /**
     * 审批流程是否结束
     */
    private Integer isEnd;

    /**
     * 是否审批通过
     */
    private Integer isPass;
}