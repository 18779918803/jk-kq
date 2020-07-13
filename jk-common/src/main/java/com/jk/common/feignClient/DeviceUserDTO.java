package com.jk.common.feignClient;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceUserDTO {

    /**
     * 设备号
     */
    private String sn;

    /**
     * 卡号 目前无用
     */
    private String card;

    /**
     * 员工id
     */
    private String ccid;

    /**
     * 员工姓名
     */
    private String name;

    /**
     * 部门编号
     */
    private String deptid;
}
