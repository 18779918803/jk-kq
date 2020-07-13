package com.jk.sw.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.jk.common.base.BaseEntity;
import com.jk.common.validator.group.InsertGroup;
import com.jk.common.validator.group.UpdateGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 流程实体类
 *
 * @author 温龙飞
 * @version 1.0
 * @date 2020年06月11日
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("sw_process")
public class SwProcess extends BaseEntity implements Comparable<SwProcess>{

    /**
     * 收文ID
     */
    @NotNull(message = "请输入正确的收文ID", groups = InsertGroup.class)
    private Integer swid;

    /**
     * 任务实例ID
     */
    @NotNull(message = "请输入正确的任务ID", groups = InsertGroup.class)
    private Integer taskId;

    /**
     * 审批人ID
     */
    @NotNull(message = "请输入正确的审批人ID", groups = InsertGroup.class)
    private Integer approver;

    /**
     * 顺序
     */
    @NotNull(message = "请输入正确的顺序", groups = InsertGroup.class)
    private Integer sort;

    /**
     * 审批状态
     */
    @NotNull(message = "请输入正确的状态", groups = UpdateGroup.class)
    private Integer status;

    /**
     * 审批人姓名
     */
    private String username;

    /**
     * 批示
     */
    private String content;

    @Override
    public int compareTo(SwProcess process) {
        if (this.getSort() == process.getSort()){
            return 0;
        }
        return this.sort < process.getSort() ? -1:1;//升序;
    }
}