package com.jk.db.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;

import com.jk.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 用户组织实体类
 *
 * @author 晏攀林
 * @version 1.0
 * @date 2020年06月21日
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("jk_user_organs")
public class UserOrgans extends BaseEntity {

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 分管公司/部门ID
     */
    private Integer organId;

}