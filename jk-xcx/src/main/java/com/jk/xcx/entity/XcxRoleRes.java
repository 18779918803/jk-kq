package com.jk.xcx.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
 * 小程序页面角色关系表实体类
 *
 * @author 温龙飞
 * @version 1.0
 * @date 2020年07月09日
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("xcx_role_res")
public class XcxRoleRes implements Serializable {

    private Integer id;
    /**
     * 小程序页面资源ID
     */
    private Integer resId;

    /**
     * 角色ID
     */
    private Integer roleId;

}