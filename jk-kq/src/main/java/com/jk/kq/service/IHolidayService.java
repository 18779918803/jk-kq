package com.jk.kq.service;

import com.jk.common.base.IBaseService;
import com.jk.kq.entity.Holiday;

import java.util.List;


/**
 * 法定节假日服务接口层
 *
 * @author 何任鹏
 * @version 1.0
 * @date 2020年05月25日
 */
public interface IHolidayService extends IBaseService<Holiday>{

    List<Holiday> selectHoliday(String date);

    Integer holiday(String date);
}
