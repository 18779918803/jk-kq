package com.jk.oauth.mapper;

import com.jk.common.base.BaseMapper;
import com.jk.oauth.entity.OauthCode;
import org.apache.ibatis.annotations.Mapper;


/**
 * 认证code表映射层
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2017年12月12日
 */
@Mapper
public interface OauthCodeMapper extends BaseMapper<OauthCode> {


}
