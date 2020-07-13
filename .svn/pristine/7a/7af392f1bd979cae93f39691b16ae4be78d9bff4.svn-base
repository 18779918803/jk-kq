package com.jk.kq.mapper;

import com.jk.common.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jk.kq.entity.Holiday;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 法定节假日映射层
 *
 * @author 何任鹏
 * @version 1.0
 * @date 2020年05月25日
 */
@Mapper
@Repository
public interface HolidayMapper extends BaseMapper<Holiday> {

    List<Holiday> selectHoliday(String date);

    Integer holiday(@Param("date") String date);
}
