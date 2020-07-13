package com.jk.sys.service;


import com.jk.common.base.IBaseService;
import com.jk.sys.entity.User;

/**
 * 用户服务接口层
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2019年12月25日
 */
public interface IUserService extends IBaseService<User> {

    /**
     * 新增用户
     *
     * @param user    用户
     * @param roleIds 角色ids
     */
    void insertUser(User user, Integer[] roleIds);

    /**
     * 更新用户
     *
     * @param user    用户
     * @param roleIds 角色ids
     */
    void updateUser(User user, Integer[] roleIds);

    /**
     * 更新密码
     *
     * @param password 密码
     */
    void updatePsw(String password);

    /**
     * 检测用户密码
     *
     * @param oldPassword 旧密码
     * @return {@link boolean}
     */
    boolean checkPsw(String oldPassword);
}
