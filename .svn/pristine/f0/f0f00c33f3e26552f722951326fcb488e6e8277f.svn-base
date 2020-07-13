package com.jk.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jk.common.base.BaseServiceImpl;
import com.jk.sys.entity.Log;
import com.jk.sys.mapper.LogMapper;
import com.jk.sys.service.ILogService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 日志服务实现层
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2019年12月25日
 */
@Service
public class LogServiceImpl extends BaseServiceImpl<LogMapper, Log> implements ILogService {

    /**
     * 添加日志
     *
     * @param log
     * @return true 添加成功 false 添加失败
     */
    @Override
    @Async("taskExecutor")
    public void insertLog(Log log) {
        super.insert(log);
    }

    /**
     * 清空日志
     */
    @Override
    public void deleteAll() {
        delete(new LambdaQueryWrapper<>());
    }
}
