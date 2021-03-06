package com.jk.kq.service.impl;

import com.jk.common.base.BaseServiceImpl;
import com.jk.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jk.kq.entity.OpenUser;
import com.jk.kq.mapper.OpenUserMapper;
import com.jk.kq.service.IOpenUserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * 用户服务接口实现层
 *
 * @author 何任鹏
 * @version 1.0
 * @date 2020年05月26日
 */
@Service
public class OpenUserServiceImpl extends BaseServiceImpl<OpenUserMapper, OpenUser> implements IOpenUserService {

    @Autowired
    private OpenUserMapper openUserMapper;

    /**
     * 通过第三方Session查找OpenUser用户信息
     *
     * @param thirdSession
     * @return
     */
    @Override
    public OpenUser selectByThirdSession(String thirdSession) {
        return openUserMapper.selectByThirdSession(thirdSession);
    }

    /**
     * 通过OpenId获取OpenUser的信息
     *
     * @param openid
     * @return
     */
    @Override
    public OpenUser selectByOpenid(String openid) {
        return openUserMapper.selectByOpenid(openid);
    }

    /**
     * 获取职位等级为分管领导的用户信息
     *
     * @return
     */
    @Override
    public List<OpenUser> getUserByPosition() {
        return openUserMapper.getUserByPosition();
    }

    /**
     * 插入分管领导分管的公司或部门
     *
     * @param openUserId
     * @param organId
     */
    @Override
    public void insertOrgan(Integer openUserId, Integer organId) {
        openUserMapper.insertOrgan(openUserId, organId);
    }

    /**
     * 通过openUserId查找该分管领导下分管的公司/部门
     *
     * @param openUserId
     * @return
     */
    @Override
    public List getOrganByOpenUserId(Integer openUserId) {
        return openUserMapper.getOrganByOpenUserId(openUserId);
    }

    /**
     * 删除分管领导子节点的公司/部门信息
     *
     * @param userOrganId
     */
    @Override
    public void deleteOrgan(Integer userOrganId) {
        openUserMapper.deleteOrgan(userOrganId);
    }

    /**
     * 通过用户部门和职位等级查找用户（给领导推送消息）
     *
     * @param organId 用户部门
     * @param grade   职位等级
     * @return
     */
    @Override
    public List<OpenUser> selectOpenUser(Integer organId, Integer grade) {
        return openUserMapper.selectOpenUser(organId, grade);
    }

    @Override
    public List<OpenUser> selectOpenUsers(String leadDepartment, Integer grade) {
        return openUserMapper.selectOpenUsers(leadDepartment, grade);
    }

    /**
     * 查找该部门负责人的分管领导
     *
     * @param organId 领导分管部门
     * @param grade   领导等级
     * @return 返回该申请需要推送的用户的集合
     */
    @Override
    public List<OpenUser> selectLeaders(Integer organId, Integer grade) {
        return openUserMapper.selectLeaders(organId, grade);
    }

    /**
     * 使用微信小程序的用户获取公众号的用户openid（批量获取）
     *
     * @param checkList 需要推送的用户的微信小程序信息集合
     * @return 返回公众号的openid集合
     */
    @Override
    public List<String> getGzhOpenIds(List<OpenUser> checkList) {
        return openUserMapper.getGzhOpenIds(checkList);
    }

    /**
     * 使用微信小程序的用户获取公众号的用户openid（单个获取）
     *
     * @param openUserId 用户id
     * @return
     */
    @Override
    public String getGzhOpenId(Integer openUserId) {
        return openUserMapper.getGzhOpenId(openUserId);
    }

    /**
     * 通过部门ID查找用户信息
     *
     * @param organId
     * @return
     */
    @Override
    public List<OpenUser> selectByOrganId(Integer organId) {
        return openUserMapper.selectByOrganId(organId);
    }

    @Override
    public User getOpenUser(HttpServletRequest request)   {
        return null;
    }

}
