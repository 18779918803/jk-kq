package com.jk.kq.entity.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatisReportVO {

    /**
     * 迟到、早退、矿工
     */
    @Excel(name="迟到、早退、矿工")
    private String type;

    /**
     * 时间，支持小数，小时为单位
     */
    @Excel(name="时间，支持小数，小时为单位")
    private BigDecimal count;

    /**
     * 以天计算，通过count小时换算，换算规则在文档中
     */
    @Excel(name="以天计算，通过count小时换算，换算规则在文档中")
    private BigDecimal day;

    /**
     * user_id
     */
    @Excel(name="user_id")
    private Integer userId;

    /**
     * 结束时间
     */
    @Excel(name="结束时间",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    /**
     * 开始时间和结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    /**
     * 日 月 年
     */
    @Excel(name="日 月 年")
    private String unit;
}
