package com.jk.api.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 已完成审批dto
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FinishDTO {
    /**
     * CheckEnums
     */
    private Integer typeId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
}
