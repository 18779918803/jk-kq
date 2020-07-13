package com.jk.gen.dto;

import com.jk.gen.entity.GenScheme;
import com.jk.gen.entity.GenTable;
import com.jk.gen.entity.GenTableColumn;
import lombok.Data;

import java.util.List;

/**
 * 生成设置DTO
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2018年05月03日
 */
@Data
public class GenSetDTO {

    /**
     * 规则设置
     */
    private GenScheme genScheme;

    /**
     * 表设置
     */
    private GenTable genTable;

    /**
     * 字段设置
     */
    private List<GenTableColumn> genTableColumns;

}
