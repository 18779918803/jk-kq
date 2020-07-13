package com.jk.sys.mapper;

import com.jk.common.base.BaseMapper;
import com.jk.sys.entity.Role;
import com.jk.sys.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户角色映射层
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2019年12月25日
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

    /**
     * 获取用户角色
     *  
     * @param userId 用户id
     * @return {@link List<Role>}
     */
    List<Role> selectUserRoles(Integer userId);
}
