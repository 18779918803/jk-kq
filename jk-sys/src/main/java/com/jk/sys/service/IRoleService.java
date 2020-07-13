package com.jk.sys.service;

import com.jk.common.base.IBaseService;
import com.jk.common.bean.TreeBean;
import com.jk.sys.entity.Role;

import java.util.List;

/**
 * 角色管理服务接口层
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2019年12月25日
 */
public interface IRoleService extends IBaseService<Role> {

    /**
     * 查询用户角色
     *
     * @param userId 用户id
     * @return {@link List<Role>}
     */
    List<Role> selectUserRoles(Integer userId);

    /**
     * 获取角色资源树
     *
     * @param roleId 角色id
     * @return List<TreeBean>
     */
    List<TreeBean> selectRoleResTree(Integer roleId);

    /**
     * 修改角色资源权限
     *
     * @param roleId 角色id
     * @param resIds 资源ids
     */
    void updateRoleRes(Integer roleId, Integer[] resIds);

    /**
     * 获取角色数据权限
     *
     * @param roleId
     * @return List<Role>
     */
    List<TreeBean> selectScopeTree(Integer roleId);

    /**
     * 设置角色的数据权限
     *
     * @param roleId   角色id
     * @param organIds 机构ids
     */
    void updateRoleScope(Integer roleId, Integer[] organIds);
}
