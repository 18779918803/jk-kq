package com.jk.kq.service.impl;

import com.jk.common.base.BaseServiceImpl;
import com.jk.kq.entity.Clock;
import com.jk.kq.service.IClockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jk.kq.mapper.ClockMapper;

import java.util.Date;
import java.util.List;


/**
 * 打卡记录服务接口实现层
 *
 * @author 何任鹏
 * @version 1.0
 * @date 2020年05月25日
 */
@Service
public class ClockServiceImpl extends BaseServiceImpl<ClockMapper, Clock> implements IClockService {

    @Autowired
    private ClockMapper clockMapper;

    @Override
    public List selectClock(Integer openUserId, String date) {
        return clockMapper.selectClock(openUserId, date);
    }

    @Override
    public List selectStatus(Integer openUserId, String date) {
        return clockMapper.selectStatus(openUserId, date);
    }

    /**
     * 检查用户在今天是否有9点之后的打卡记录
     *
     * @param openUserId
     * @param toDay
     * @return
     */
    @Override
    public Integer checkPunch(Integer openUserId, Date toDay) {
        return clockMapper.checkPunch(openUserId,toDay);
    }


}
