package com.jk.oauth.mapper;

import com.jk.common.base.BaseMapper;
import com.jk.oauth.entity.ClientDetails;
import org.apache.ibatis.annotations.Mapper;


/**
 * 客户端详情映射层
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2017年12月20日
 */
@Mapper
public interface ClientDetailsMapper extends BaseMapper<ClientDetails> {

}
