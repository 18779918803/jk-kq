package com.jk.kq.service;

import com.jk.common.base.IBaseService;
import com.jk.kq.entity.OpenUser;
import com.jk.sys.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * 用户服务接口层
 *
 * @author 何任鹏
 * @version 1.0
 * @date 2020年05月26日
 */
public interface IOpenUserService extends IBaseService<OpenUser> {


    /**
     * 通过第三方Session查找OpenUser用户信息
     *
     * @param thirdSession
     * @return
     */
    OpenUser selectByThirdSession(String thirdSession);

    /**
     * 通过OpenId获取OpenUser的信息
     *
     * @param openid
     * @return
     */
    OpenUser selectByOpenid(String openid);

    /**
     * 获取职位等级为分管领导的用户信息
     *
     * @return
     */
    List<OpenUser> getUserByPosition();

    /**
     * 插入分管领导分管的公司或部门
     *
     * @param openUserId
     * @param organId
     */
    void insertOrgan(Integer openUserId, Integer organId);

    /**
     * 通过openUserId查找该分管领导下分管的公司/部门
     *
     * @param openUserId
     * @return
     */
    List getOrganByOpenUserId(Integer openUserId);

    /**
     * 删除分管领导子节点的公司/部门信息
     *
     * @param userOrganId
     */
    void deleteOrgan(Integer userOrganId);


    /**
     * 通过用户部门和职位等级查找用户（给领导推送消息）
     *
     * @param organId 用户部门
     * @param grade   职位等级
     * @return
     */
    List<OpenUser> selectOpenUser(Integer organId, Integer grade);

    /**
     * 通过用户部门和职位等级查找用户（给领导推送消息）
     *
     * @param leadDepartment 用户部门名称
     * @param grade   职位等级
     * @return
     */
    List<OpenUser> selectOpenUsers(String leadDepartment, Integer grade);

    /**
     * 查找该部门负责人的分管领导
     *
     * @param organId 领导分管部门
     * @param grade   领导等级
     * @return 返回该申请需要推送的用户的集合
     */
    List<OpenUser> selectLeaders(Integer organId, Integer grade);


    /**
     * 使用微信小程序的用户获取公众号的用户openid（批量获取）
     *
     * @param checkList 需要推送的用户的微信小程序信息集合
     * @return 返回公众号的openid集合
     */
    List<String> getGzhOpenIds(List<OpenUser> checkList);



    /**
     * 使用微信小程序的用户获取公众号的用户openid（单个获取）
     *
     * @param openUserId 用户id
     * @return
     */
    String getGzhOpenId(Integer openUserId);

    /**
     * 通过部门ID查找用户信息
     *
     * @param organId
     * @return
     */
    List<OpenUser> selectByOrganId(Integer organId);
    /**
     * 获取OpenUser用户信息
     *
     * @param request
     * @return
     */
    User getOpenUser(HttpServletRequest request)  ;

}
