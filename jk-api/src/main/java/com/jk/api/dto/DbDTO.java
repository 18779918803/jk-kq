package com.jk.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {{文件描述}}
 *
 * @author 刘杭
 * @version 1.0
 * @date 2020年06月22日
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DbDTO {

    /**
     * 对应CheckEnums
     */
    private Integer typePId;
}
