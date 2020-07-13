package com.jk.api.service;

import com.jk.api.dto.SessionDTO;
import com.jk.kq.entity.OpenUser;
import com.jk.sys.entity.Role;
import com.jk.sys.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 这个类是用来处理和微信接口相关的问题的
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2020年03月27日
 */
public interface ApiService {

    /**
     * 获取请求中的第三方Session
     *
     * @param request
     * @return
     */
    String getThirdSession(HttpServletRequest request);

    /**
     * 获取OpenUser用户信息
     *
     * @param request
     * @return
     */
    OpenUser getOpenUser(HttpServletRequest request);

    /**
     * 获取OpenUser用户信息
     *
     * @param request
     * @return
     */
    User getUser(HttpServletRequest request);

    /**
     * 通过请求和第三方Session查找Session_key
     *
     * @param request
     * @param thirdSession
     * @return
     */
    SessionDTO selectSession(HttpServletRequest request, String thirdSession);


    public int selectRole(List<Role> roles);


    void cancel(Integer id, User user, HttpServletRequest request);


     List<User> nextHandel(String instanceId);

    /**
     * 日期和 上 下
     * @param date
     * @param flag
     */
    void updateClock(Date date, int flag,int userId);

}
