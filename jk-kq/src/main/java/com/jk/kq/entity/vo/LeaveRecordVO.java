package com.jk.kq.entity.vo;


import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jk.activiti.domain.HistoricActivity;
import com.jk.common.base.BaseEntity;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LeaveRecordVO  extends BaseEntity {


    /**
     * open_user_id
     */
    private Long openUserId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 标题
     */
    private String title;

    /**
     * 公司ID/部门ID
     */
    private Integer organId;

    /**
     * 请假时间1上午2下午（请半天才有值）
     */
    private Integer timeId;

    /**
     * 公司/部门
     */
    private String organ;

    /**
     * 开始时间
     */
    private String startDate;

    /**
     * 结束时间
     */
    private String endDate;

    /**
     * 请假原因
     */
    private String reason;

    /**
     * 请假天数
     */
    private BigDecimal day;


    /**
     * 请假类型 对应work_type
     */
    private Integer typeId;

    private Date insertTime;



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

    /**
     * 1 事假 2 加班  前端处理
     */
    private Integer pid;

    /**
     * jk_work_type name
     */
    private String typeName;

    private String timeName;

    /**
     * 审批流程是否结束
     */
    private Integer isEnd;

    /**
     * 是否审批通过
     */
    private Integer isPass;



    /**
     * 审批历史
     */
    private List<HistoricActivity> his;

    /**
     * 下一个审批
     */
    private String toDoTaskName;

}
