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
public class CheckDTO {

    /**
     * 对应CheckEnums
     */
    private Integer typePId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

}
