package com.jk.kq.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OverTimeRecordDTO {


    /**
     * 开始日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate startDate;

    /**
     * 结束日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate endDate;


    /**
     * 日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date date;


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
     * 加班原因
     */
    private String reason;

    /**
     * 加班天数 只能为0.5 1 1.5
     */
    private BigDecimal day;

    /**
     * 申请状态
     */
    private Integer state;

    /**
     * 申请审批完成状态
     */
    private Integer topState;

    /**
     * 加班类型
     */
    private Integer typeId;

}
