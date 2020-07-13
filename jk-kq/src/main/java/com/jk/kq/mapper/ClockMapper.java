package com.jk.kq.mapper;

import com.jk.common.base.BaseMapper;
import com.jk.kq.entity.Clock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 打卡记录映射层
 *
 * @author 何任鹏
 * @version 1.0
 * @date 2020年05月25日
 */
@Mapper
@Repository
public interface ClockMapper extends BaseMapper<Clock> {

    /**
     * 通过OpenUserId和当前日期查找打卡记录信息
     *
     * @param userId
     * @param date
     */
    List selectClock(@Param("openUserId") Integer openUserId, @Param("date") String date);

    List selectStatus(@Param("openUserId") Integer openUserId, @Param("date") String date);

    /**
     * 检查用户在今天是否有9点之后的打卡记录
     * @param openUserId
     * @param toDay
     * @return
     */
    Integer checkPunch(Integer openUserId, Date toDay);

}
