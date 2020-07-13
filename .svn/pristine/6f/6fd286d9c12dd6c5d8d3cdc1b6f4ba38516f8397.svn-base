package com.jk.kq.mapper;

import com.jk.common.base.BaseMapper;
import com.jk.kq.entity.OpenUser;
import com.jk.kq.entity.OvertimeRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 加班申请映射层
 *
 * @author 何任鹏
 * @version 1.0
 * @date 2020年06月01日
 */
@Mapper
public interface OvertimeRecordMapper extends BaseMapper<OvertimeRecord> {

    List selectHistory(@Param("userId") Integer userId, @Param("date") String date);

    /**
     * 查找用户可以审核的加班申请
     *
     * @param queryMap
     * @return
     */
    List selectToCheck(Map queryMap);


    List<OpenUser> selectOpenUser();
}
