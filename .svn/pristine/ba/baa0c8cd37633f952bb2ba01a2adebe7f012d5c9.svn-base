package com.jk.gen.mapper;

import com.jk.common.base.BaseMapper;
import com.jk.gen.entity.GenTableColumn;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 业务表字段映射层
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2018年05月03日
 */
@Mapper
public interface GenTableColumnMapper extends BaseMapper<GenTableColumn> {

    /**
     * 获取物理表字段信息
     *
     * @param tableName
     * @return List<GenTableColumn>
     */
    List<GenTableColumn> selectDbTableColumn(@Param("tableName") String tableName);

}
