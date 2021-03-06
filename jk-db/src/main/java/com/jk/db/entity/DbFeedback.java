package com.jk.db.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.jk.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 督查督办情况反馈表实体类
 *
 * @author 刘杭
 * @version 1.0
 * @date 2020年06月08日
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("db_feedback")
public class DbFeedback extends BaseEntity {

    /**
     * 督办类别
     */
    private String dbType;

    /**
     * 编号
     */
    private Integer dbId;

    /**
     * 督办事项来源
     */
    private String dbSource;

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
     * 重要程度（A,B,C）
     */
    private String dbLevel;

    /**
     * 牵头部门（公司）
     */
    private String leadDepartment;


    /**
     * 责任部门（公司）
     */
    private String responsibilityDepartment;
    private Integer leadDepartmentId;

    private  Integer  responsibilityDepartmentId;

    /**
     * 实际完成时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date totalTime;

    /**
     * 完成佐证（最终完成情况及佐证材料目录）
     */
    private String finalEvidence;

    /**
     * 牵头部门负责人
     */
    private String departmentPrincipal;

    /**
     * 牵头部门分管领导
     */
    private String chargeLead;

    /**
     * 总经理意见
     */
    private String managerAdvice;

    /**
     * 董事长意见
     */
    private String presidentAdvice;


    @TableField(exist = false)
    /** 申请人姓名 */
    private String applyUserName;

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
     * 流程实例ID
     */
    private String instanceId;

    /**
     * 更新者
     */
    private String updateBy;

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