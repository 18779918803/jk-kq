package com.jk.oauth.service;

import com.jk.common.base.IBaseService;
import com.jk.oauth.entity.ClientDetails;

/**
 * 客户端详情服务层
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2017年12月20日
 */
public interface IClientDetailsService extends IBaseService<ClientDetails> {

    /**
     * 根据客户端id查询
     *
     * @param clientId 客户端id
     * @return ClientDetails 客户端详情
     */
    ClientDetails selectByClientId(String clientId);
}
