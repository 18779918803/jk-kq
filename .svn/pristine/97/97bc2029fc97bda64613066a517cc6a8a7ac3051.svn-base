package com.jk.sys.service.impl;

import com.jk.common.base.BaseServiceImpl;
import com.jk.sys.entity.Role;
import com.jk.sys.entity.RoleScope;
import com.jk.sys.entity.User;
import com.jk.sys.mapper.RoleScopeMapper;
import com.jk.sys.service.IRoleScopeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色资源实现层
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2019年12月25日
 */
@Service
public class RoleScopeServiceImpl extends BaseServiceImpl<RoleScopeMapper, RoleScope> implements IRoleScopeService {


    /**
     * 查询用户的数据权限
     *
     * @param user 用户
     * @return List<Integer> 权限id list
     */
    @Override
    public List<String> selectUserDataScope(User user) {
        List<Role> list = user.getRoles();
        if (list != null) {
            Integer[] roleIds = new Integer[list.size()];
            for (int i = 0; i < list.size(); i++) {
                Role role = list.get(i);
                roleIds[i] = role.getId();
            }
            if (roleIds.length > 0) {
                return baseMapper.selectRoleDataId(roleIds);
            }
        }
        return new ArrayList<>();
    }
}
