package com.jk.kq.service.impl;

import com.jk.common.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jk.kq.entity.Holiday;
import com.jk.kq.mapper.HolidayMapper;
import com.jk.kq.service.IHolidayService;

import java.util.List;


/**
 * 法定节假日服务接口实现层
 *
 * @author 何任鹏
 * @version 1.0
 * @date 2020年05月25日
 */
@Service
public class HolidayServiceImpl extends BaseServiceImpl<HolidayMapper, Holiday> implements IHolidayService {

    @Autowired
    private HolidayMapper holidayMapper;

    @Override
    public List<Holiday> selectHoliday(String date) {
        return holidayMapper.selectHoliday(date);
    }

    @Override
    public Integer holiday(String date) {
        return holidayMapper.holiday(date);
    }
}
