package com.jk.sys.service;

import com.jk.sys.entity.Organ;
import com.jk.common.base.IBaseService;

import java.util.List;

/**
 * 组织机构服务接口层
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2019年12月25日
 */
public interface IOrganService extends IBaseService<Organ> {

    /**
     * 根据pid查询组织机构
     *
     * @param pid 机构pid
     * @return {@link List<Organ>}
     */
    List<Organ> selectByPid(Integer pid);

    /**
     * 根据机构code查询
     *
     * @param organCode
     * @return 组织机构
     */
    Organ selectByCode(String organCode);

    /**
     * 查询角色的部门资源
     * @param roleId
     * @return List<Organ>
     */
    List<Organ> selectRoleOrgan(Integer roleId);
}
