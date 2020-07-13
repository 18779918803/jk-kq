package com.jk.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jk.common.base.BaseEntity;
import com.jk.common.validator.group.InsertGroup;
import com.jk.common.validator.group.UpdateGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 区域实体类
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2019年12月25日
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("sys_region")
public class Region extends BaseEntity {

    /**
     * 地区编码
     */
    @NotBlank(message = "地区编码不能为空", groups = {InsertGroup.class, UpdateGroup.class})
    private String code;

    /**
     * 地区名称
     */
    @NotBlank(message = "地区名称不能为空", groups = {InsertGroup.class, UpdateGroup.class})
    private String name;

    /**
     * 区域类型编码
     */
    @NotBlank(message = "区域类型不能为空", groups = {InsertGroup.class, UpdateGroup.class})
    private String type;

    /**
     * 父id
     */
    private Integer pid;

    /**
     * 是否包含子区域 'true' 'false'
     */
    @TableField(exist = false)
    private String hasChild;
}
