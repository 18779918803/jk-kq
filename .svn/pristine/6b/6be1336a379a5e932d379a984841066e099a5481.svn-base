package com.jk.kq.mapper;

import com.jk.common.base.BaseMapper;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import com.jk.kq.entity.WorkType;

import java.util.List;

/**
 * 考勤工作类型映射层
 *
 * @author 何任鹏
 * @version 1.0
 * @date 2020年05月28日
 */
@Mapper
public interface WorkTypeMapper extends BaseMapper<WorkType> {

    /**
     * 根据PID查找所有的父类型
     *
     * @param pid
     * @return
     */
    List<WorkType> selectByPid(Integer pid);

    /**
     * 根据Pid删除所有数据
     *
     * @param pid
     */
    void deleteByPid(Integer pid);
}
