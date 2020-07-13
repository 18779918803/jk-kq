package com.jk.file.mapper;

import com.jk.common.base.BaseMapper;
import com.jk.file.entity.FileResult;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 文件映射层
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2018年09月11日
 */
@Mapper
@Repository
public interface FileResultMapper extends BaseMapper<FileResult>{


}
