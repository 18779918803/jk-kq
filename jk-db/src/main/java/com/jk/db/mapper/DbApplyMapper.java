package com.jk.db.mapper;

import com.jk.common.base.BaseMapper;
import com.jk.sys.entity.User;
import org.apache.ibatis.annotations.Mapper;
import com.jk.db.entity.DbApply;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 督查督办申请表映射层
 *
 * @author 刘杭
 * @version 1.0
 * @date 2020年06月10日
 */
@Mapper
public interface DbApplyMapper extends BaseMapper<DbApply> {

    List<User> getUserByRoleAndOrgan(@Param("name") String name,@Param("organId")  Integer organId);
}
