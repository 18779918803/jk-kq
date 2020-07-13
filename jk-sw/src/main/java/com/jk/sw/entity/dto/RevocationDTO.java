package com.jk.sw.entity.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 文件描述：
 *
 * @Author 温龙飞
 * @Date $ $
 * @Version 1.0
 */
@Data
public class RevocationDTO implements Serializable {

    private Integer taskId;

    private Integer status;
}
