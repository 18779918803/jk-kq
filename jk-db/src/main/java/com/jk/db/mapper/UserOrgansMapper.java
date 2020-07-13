package com.jk.db.mapper;

import com.jk.common.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jk.db.entity.UserOrgans;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户组织映射层
 *
 * @author 晏攀林
 * @version 1.0
 * @date 2020年06月21日
 */
@Mapper
public interface UserOrgansMapper extends BaseMapper<UserOrgans> {
    List<String> getUserOrganByOrgan(@Param("organId") Integer organId);
}
