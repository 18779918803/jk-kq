package com.jk.sys.mapper;


import com.jk.common.base.BaseMapper;
import com.jk.sys.entity.Organ;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 机构映射层
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2019年12月25日
 */
@Mapper
public interface OrganMapper extends BaseMapper<Organ> {

    /**
     * 根据pid查询组织机构
     *
     * @param pid 机构pid
     * @return 组织机构
     */
    List<Organ> selectByPid(@Param("pid") Integer pid);

    /**
     * 查询角色部门资源
     *
     * @param roleId 角色id
     * @return List<Organ>
     */
    List<Organ> selectRoleOrgan(Integer roleId);
}
