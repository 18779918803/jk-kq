package com.jk.sys.mapper;

import com.jk.common.base.BaseMapper;
import com.jk.sys.entity.RoleScope;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色数据资源
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2019年12月25日
 */
@Mapper
public interface RoleScopeMapper extends BaseMapper<RoleScope> {


    /**
     * 获取角色数据权限
     *
     * @param roleIds
     * @return {@link List<String>}
     */
    List<String> selectRoleDataId(Integer[] roleIds);
}
