package com.jk.sw.entity;

import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.jk.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 任务实体类
 *
 * @author 温龙飞
 * @version 1.0
 * @date 2020年06月07日
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("sw_task")
public class SwTask extends BaseEntity implements Comparable<SwTask> {

    /**
     * 任务实例ID
     */
    private String taskId;

    /**
     * 附件
     */
    private String attachment;

    /**
     * 拟办意见
     */
    private String propose;

    /**
     * 收文ID
     */
    private Integer swid;

    /**
     * 任务状态
     */
    private Integer status;

    /**
     * 文件文本
     */
    private String text;

    @Override
    public int compareTo(SwTask task) {
        if (this.getId() == task.getId()){
            return 0;
        }
        return this.getId() > task.getId() ? -1:1;//降序
    }
}