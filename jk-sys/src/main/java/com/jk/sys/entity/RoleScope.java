package com.jk.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色数据权限实体
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2019年12月25日
 */
@Data
@NoArgsConstructor
@TableName("sys_role_scope")
public class RoleScope {
    /**
     * 数据库id
     */
    @TableId
    private Integer id;

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 权限字段
     */
    private String dataId;
}

