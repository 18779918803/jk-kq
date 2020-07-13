package com.jk.db.mapper;

import com.jk.common.base.BaseMapper;
import com.jk.sys.entity.User;
import org.apache.ibatis.annotations.Mapper;
import com.jk.db.entity.DbDelay;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 督查督办延期（撤销）申请表映射层
 *
 * @author 刘杭
 * @version 1.0
 * @date 2020年06月10日
 */
@Mapper
public interface DbDelayMapper extends BaseMapper<DbDelay> {


    List<User> getUserByRoleAndOrgan(@Param("name") String name, @Param("organId")  Integer organId);
}
