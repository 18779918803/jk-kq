package com.jk.gen.service;

import com.jk.common.base.IBaseService;
import com.jk.gen.entity.GenTable;
import com.jk.gen.entity.GenTableColumn;

import java.util.List;

/**
 * 业务表字段服务接口层
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2018年05月03日
 */
public interface IGenTableColumnService extends IBaseService<GenTableColumn> {

    /**
     * 查询表字段设置信息（物理表）
     *
     * @param genTable 数据表
     * @return 表字段设置信息
     */
    List<GenTableColumn> selectDbTableColumn(GenTable genTable);

    /**
     * 根据tableId删除
     *
     * @param tableId 表id
     */
    void deleteByTableId(Integer tableId);

}
