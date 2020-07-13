package com.jk.kq.service;

import com.jk.common.base.IBaseService;
import com.jk.kq.entity.WorkType;

import java.util.List;


/**
 * 考勤工作类型服务接口层
 *
 * @author 何任鹏
 * @version 1.0
 * @date 2020年05月28日
 */
public interface IWorkTypeService extends IBaseService<WorkType> {

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
