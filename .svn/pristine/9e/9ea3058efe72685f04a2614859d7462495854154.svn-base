package com.jk.sys.mapper;

import com.jk.sys.entity.Role;
import com.jk.common.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色管理映射层
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2019年12月25日
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 查询用户角色
     *
     * @param userId 用户id
     * @return List<Role>
     */
    List<Role> selectRolesByUserId(@Param("userId") Integer userId);
}
