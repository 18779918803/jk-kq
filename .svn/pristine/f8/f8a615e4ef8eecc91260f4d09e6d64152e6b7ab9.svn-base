package com.jk.sys.mapper;

import com.jk.common.base.BaseMapper;
import com.jk.sys.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 用户映射层
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2019年12月25日
 */
@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {

    Integer getUserId(@Param("username") String username);

}
