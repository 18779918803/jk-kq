package com.jk.kq.service.impl;

import com.jk.common.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jk.kq.entity.WorkType;
import com.jk.kq.mapper.WorkTypeMapper;
import com.jk.kq.service.IWorkTypeService;

import java.util.List;


/**
 * 考勤工作类型服务接口实现层
 *
 * @author 何任鹏
 * @version 1.0
 * @date 2020年05月28日
 */
@Service
public class WorkTypeServiceImpl extends BaseServiceImpl<WorkTypeMapper, WorkType> implements IWorkTypeService {

    @Autowired
    private WorkTypeMapper workTypeMapper;

    /**
     * 根据PID查找所有的父类型
     *
     * @param pid
     * @return
     */
    @Override
    public List<WorkType> selectByPid(Integer pid) {
        return workTypeMapper.selectByPid(pid);
    }

    @Override
    public void deleteByPid(Integer pid) {
        workTypeMapper.deleteByPid(pid);
    }
}
