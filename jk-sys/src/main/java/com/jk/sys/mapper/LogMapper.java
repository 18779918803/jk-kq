package com.jk.sys.mapper;


import com.jk.common.base.BaseMapper;
import com.jk.sys.entity.Log;
import org.apache.ibatis.annotations.Mapper;

/**
 * 日志管理映射层
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2019年12月25日
 */
@Mapper
public interface LogMapper extends BaseMapper<Log> {


}