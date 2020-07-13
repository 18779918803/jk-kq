package com.jk.kq.service;

import com.jk.common.base.IBaseService;
import com.jk.kq.entity.GzhOpenUser;


/**
 * 公众号用户服务接口层
 *
 * @author 何任鹏
 * @version 1.0
 * @date 2020年06月09日
 */
public interface IGzhOpenUserService extends IBaseService<GzhOpenUser> {

    /**
     * 通过openId来查看该用户的记录信息是否已经插入数据库中
     * @param openId
     * @return  有则返回实体，无则返回null
     */
    GzhOpenUser selectByOpenId(String openId);

}
