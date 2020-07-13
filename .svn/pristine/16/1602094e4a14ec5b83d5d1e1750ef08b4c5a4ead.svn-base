package com.jk.api.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WriteoffRecordDTO {

    /**
     * open_user_id
     */
    private Integer openUserId;

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
     * 申请状态
     */
    private Integer state;

    /**
     * 申请审批完成状态
     */
    private Integer topState;

    /**
     * 核销类型
     */
    private Integer typeId;
}
