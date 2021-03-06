package com.jk.kq.mapper;

import com.jk.common.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jk.kq.entity.GzhOpenUser;
import org.springframework.stereotype.Repository;

/**
 * 公众号用户映射层
 *
 * @author 何任鹏
 * @version 1.0
 * @date 2020年06月09日
 */
@Mapper
@Repository
public interface GzhOpenUserMapper extends BaseMapper<GzhOpenUser> {

    /**
     * 通过openId来查看该用户的记录信息是否已经插入数据库中
     * @param unionid
     * @return  有则返回实体，无则返回null
     */
    GzhOpenUser selectByOpenId(String unionid);


}
