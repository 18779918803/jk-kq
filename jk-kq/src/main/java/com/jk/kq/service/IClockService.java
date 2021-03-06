package com.jk.kq.service;

import com.jk.common.base.IBaseService;
import com.jk.kq.entity.Clock;

import java.util.Date;
import java.util.List;


/**
 * 打卡记录服务接口层
 *
 * @author 何任鹏
 * @version 1.0
 * @date 2020年05月25日
 */
public interface IClockService extends IBaseService<Clock> {

    /**
     * 通过userId和当前日期查找打卡记录信息
     *
     * @param userId
     * @param date
     */
    List selectClock(Integer openUserId, String date);

    /**
     * 通过userId和当前日期查找当月打卡状态信息
     *
     * @param userId
     * @param date
     */
    List selectStatus(Integer openUserId, String date);


    /**
     * 检查用户在今天是否有9点之后的打卡记录
     *
     * @param openUserId
     * @param toDay
     * @return
     */
    Integer checkPunch(Integer openUserId, Date toDay);

}
