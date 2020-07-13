package com.jk.kq.entity.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jk.activiti.domain.HistoricActivity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OvertimeRecordVo {

    private Integer id;

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

    private String date;

    /**
     * 加班原因
     */
    private String reason;

    /**
     * 加班时长 0.5 1
     */
    private BigDecimal day;

//    /**
//     * 申请状态
//     */
//    private Integer state;
//
//    /**
//     * 申请审批完成状态
//     */
//    private Integer topState;

    /**
     * 加班类型
     */
    private Integer typeId;

    /**
     * 类型
     */
    private String typeName;

    private Integer Pid;

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

}
