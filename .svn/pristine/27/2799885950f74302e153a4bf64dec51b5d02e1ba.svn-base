package com.jk.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jk.common.base.BaseEntity;
import com.jk.common.validator.group.InsertGroup;
import com.jk.common.validator.group.UpdateGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 角色实体类
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2019年12月25日
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("sys_role")
public class Role extends BaseEntity {

    /**
     * 角色名
     */
    @NotBlank(message = "角色名不能为空", groups = {InsertGroup.class, UpdateGroup.class})
    private String name;

    /**
     * 角色编码
     */
    @NotBlank(message = "角色编码不能为空", groups = {InsertGroup.class, UpdateGroup.class})
    private String code;

    /**
     * 备注
     */
    private String remark;

    /**
     * 排序
     */
    @NotNull(message = "排序不能为空", groups = {InsertGroup.class, UpdateGroup.class})
    private Integer sort;

}