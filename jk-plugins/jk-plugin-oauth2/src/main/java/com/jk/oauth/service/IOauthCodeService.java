package com.jk.oauth.service;

import com.jk.common.base.IBaseService;
import com.jk.oauth.entity.OauthCode;

/**
 * 客户端详情服务层
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2017年12月20日
 */
public interface IOauthCodeService extends IBaseService<OauthCode> {

    /**
     * 查询认证编码
     *
     * @param username 用户名
     * @param clientId 客户端
     * @return OauthCode
     */
    OauthCode selectOauthCodeByUsernameClientId(String username, String clientId);

    /**
     * 查询认证编码
     * @param code 认证编码
     * @param clientId 客户端
     * @return
     */
    OauthCode selectOauthCodeByCodeAndClientId(String code, String clientId);
}
