package com.jk.sw.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 收文通用映射层
 *
 * @author 温龙飞
 * @version 1.0
 * @date 2020年06月29日
 */
@Mapper
public interface SwCommonMapper {

    /**
     * 通过系统用户ID查询公众号OpenID
     * */
    String selectGZHOpenIDBySysUserId(@Param("userId") Integer userId);

    /**
     *  查询不含该角色的用户
     * */
    List selectUsersByIsNotRoleId(@Param("roleId") Integer roleId);

    /**
     *  查询为该角色的用户ID和姓名
     * */
    List selectUsersByRoleName(@Param("name") String name);

    /**
     *  查询为该角色的用户ID
     * */
    List selectUserIdsByRoleName(@Param("name") String name);

    /**
     *  查询该用户所在部门全体用户
     * */
    List selectUserOrganByUid(@Param("uid") Integer uid);

    /**
     *  通过部门查询其所有用户
     * */
    List selectUserByOrganId(@Param("organId") Integer organId);

    /**
     * 通过批量系统用户ID批量查询公众号OpenID
     * */
    List selectGZHOpenIDSBySysUserIds(@Param("userIds") Integer [] userIds);
}
