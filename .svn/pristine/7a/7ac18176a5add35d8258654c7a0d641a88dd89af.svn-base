package com.jk.kq.entity.vo;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CheckVO {


    /**
     * id
     */
    private Integer id;

    /**
     * 1 事假 2 加班  前端处理
     */
    private Integer pid;


    /**
     * 1 事假 2 加班  前端处理
     */
    private Integer typeId;

    /**
     * 申请人
     */
    private String applyUser;


    /**
     * 申请人
     */
    private String name;


    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 公司ID/部门ID
     */
    private Integer organId;

    /**
     * 公司/部门
     */
    private String organ;

    /**
     * jk_work_type name
     */
    private String typeName;

    private String timeName;

    /**
     * 请假天数
     */
    private BigDecimal day;

    /**
     * 请假原因
     */
    private String reason;

    /**
     * 请假时间
     */
    private String startDate;
    /**
     * 请假结束时间
     */
    private String endDate;


    /**
     * 核销时间
     */
    private String date;

    private Date insertTime;

   /**
     * 请假时间1上午2下午
     */
    private Integer timeId;


}
