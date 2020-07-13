package com.jk.kq.service.impl;

import com.jk.common.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jk.kq.entity.OndutyRecord;
import com.jk.kq.mapper.OndutyRecordMapper;
import com.jk.kq.service.IOndutyRecordService;

import java.util.Date;
import java.util.List;


/**
 * 值班记录服务接口实现层
 *
 * @author 何任鹏
 * @version 1.0
 * @date 2020年05月26日
 */
@Service
public class OndutyRecordServiceImpl extends BaseServiceImpl<OndutyRecordMapper, OndutyRecord> implements IOndutyRecordService {

    @Autowired
    private OndutyRecordMapper ondutyRecordMapper;

    /**
     * 通过openUserId和月份查找所有的值班记录
     *
     * @param openUserId
     * @param date
     * @return
     */
    @Override
    public List<OndutyRecord> selectByOpenUserIdAndDate(Long openUserId, Date date) {
        return ondutyRecordMapper.selectByOpenUserIdAndDate(openUserId, date);
    }

    /**
     * 查找在今天值班的记录
     *
     * @param toDay
     * @return
     */
    @Override
    public List selectOnToDay(Date toDay) {
        return ondutyRecordMapper.selectOnToDay(toDay);
    }
}
