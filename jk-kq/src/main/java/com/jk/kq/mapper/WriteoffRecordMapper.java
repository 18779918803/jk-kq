package com.jk.kq.mapper;

import com.jk.common.base.BaseMapper;
import com.jk.kq.entity.WriteoffRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 核销申请映射层
 *
 * @author 何任鹏
 * @version 1.0
 * @date 2020年06月01日
 */
@Mapper
public interface WriteoffRecordMapper extends BaseMapper<WriteoffRecord> {

    List selectHistory(@Param("userId") Integer userId, @Param("date") String date);

    /**
     * 找该用户等级可以审核的核销申请
     *
     * @param queryMap
     * @return
     */
    List selectToCheck(Map queryMap);
}
