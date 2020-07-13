package com.jk.sys.service;

import com.jk.common.base.IBaseService;
import com.jk.sys.entity.RoleScope;
import com.jk.sys.entity.User;

import java.util.List;

/**
 * 角色数据权限服务接口层
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2019年12月25日
 */
public interface IRoleScopeService extends IBaseService<RoleScope> {

    
    /**
     * 获取用户的数据权限
     *
     * @param user
     * @return {@link List<String>}
     */
    List<String> selectUserDataScope(User user);
}
