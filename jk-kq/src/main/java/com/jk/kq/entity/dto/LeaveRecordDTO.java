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
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LeaveRecordDTO {

    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    /**
     * open_user_id
     */
    private Long openUserId;

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
     * 请假原因
     */
    private String reason;

    /**
     * 请假天数
     */
    private BigDecimal day;

    /**
     * 请假时间1上午2下午（请半天才有值）
     */
    private Integer timeId;

    /**
     * 请假类型 对应work_type
     */
    private Integer typeId;

}
