package com.jk.sw.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 文件描述：
 *
 * @Author 温龙飞
 * @Date $ $
 * @Version 1.0
 */
@Data
public class InstructionDTO implements Serializable {

    @NotNull(message = "收文ID不能为空")
    private Integer swid;

    @NotNull(message = "批示内容不能为空")
    private String content;

    @NotNull(message = "状态不能为空")
    private Integer status;
}
