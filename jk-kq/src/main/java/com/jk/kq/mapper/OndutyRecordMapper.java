package com.jk.kq.mapper;

import com.jk.common.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jk.kq.entity.OndutyRecord;

import java.util.Date;
import java.util.List;

/**
 * 值班记录映射层
 *
 * @author 何任鹏
 * @version 1.0
 * @date 2020年05月26日
 */
@Mapper
public interface OndutyRecordMapper extends BaseMapper<OndutyRecord> {

    /**
     * 通过openUserId和月份查找所有的值班记录
     *
     * @param openUserId
     * @param date
     * @return
     */
    List<OndutyRecord> selectByOpenUserIdAndDate(Long openUserId, Date date);

    /**
     * 查找在今天值班的记录
     *
     * @param toDay
     * @return
     */
    List selectOnToDay(Date toDay);
}
