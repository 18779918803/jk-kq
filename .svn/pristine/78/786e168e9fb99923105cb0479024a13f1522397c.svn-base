package com.jk.kq.service.impl;

import com.jk.common.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jk.kq.entity.GzhOpenUser;
import com.jk.kq.mapper.GzhOpenUserMapper;
import com.jk.kq.service.IGzhOpenUserService;


/**
 * 公众号用户服务接口实现层
 *
 * @author 何任鹏
 * @version 1.0
 * @date 2020年06月09日
 */
@Service
public class GzhOpenUserServiceImpl extends BaseServiceImpl<GzhOpenUserMapper, GzhOpenUser> implements IGzhOpenUserService {

    @Autowired
    private GzhOpenUserMapper gzhOpenUserMapper;

    /**
     * 通过openId来查看该用户的记录信息是否已经插入数据库中
     * @param openId
     * @return  有则返回实体，无则返回null
     */
    @Override
    public GzhOpenUser selectByOpenId(String openId){
        return gzhOpenUserMapper.selectByOpenId(openId);
    }



}
