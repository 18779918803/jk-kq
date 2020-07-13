package com.jk.sys.service;

import com.jk.common.bean.DataTable;
import org.apache.shiro.session.Session;

import java.util.Map;

/**
 * session管理服务接口层
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2019年12月25日
 */
public interface ISessionService {

    /**
     * 获取在线session信息
     *
     * @param para 分页参数
     * @return 在线信息
     */
    DataTable selectPageList(Map para);

    /**
     * 获取session
     *
     * @param sessionId
     * @return session
     */
    Session getSessionById(String sessionId);

    /**
     * 踢出用户
     *
     * @param sessionId
     */
    void deleteUser(String sessionId);

    /**
     * 批量踢出用户
     *
     * @param sessionIds
     */
    void deleteUsers(String sessionIds);
}
