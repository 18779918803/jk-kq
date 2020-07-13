package com.jk.kq.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.jk.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 考勤工作类型实体类
 *
 * @author 何任鹏
 * @version 1.0
 * @date 2020年05月28日
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("jk_work_type")
public class WorkType extends BaseEntity {

    /**
     * 工作类型
     */
    private String name;

    /**
     * 备注
     */
    private String remark;

    /**
     * 父类ID
     */
    private Integer pid;

}