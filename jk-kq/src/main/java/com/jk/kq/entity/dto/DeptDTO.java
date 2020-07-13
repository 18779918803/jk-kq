package com.jk.kq.entity.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeptDTO {

    /**
     * id
     */
    private String id;

    private String pid;

    /**
     * 部门名字
     */
    private String name;

    private String sn;
}
