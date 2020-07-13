package com.jk.oauth.authz.handler;

import com.alibaba.fastjson.JSON;
import com.jk.common.bean.ReturnBean;
import com.jk.common.util.ServletUtils;
import com.jk.common.validator.Assert;
import com.jk.oauth.authz.OauthTokenxRequest;
import com.jk.oauth.authz.base.AbstractOauthTokenHandler;
import com.jk.oauth.authz.validator.AbstractOauthTokenValidator;
import com.jk.oauth.authz.validator.ClientCredentialsClientDetailsValidator;
import com.jk.oauth.entity.OauthToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.stereotype.Component;

/**
 * 客户端模式（client credentials）处理器
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2017年12月09日
 */
@Slf4j
@Component
public class ClientCredentialsTokenHandler extends AbstractOauthTokenHandler {

    @Override
    public boolean support(OauthTokenxRequest tokenRequest) {
        final String grantType = tokenRequest.getGrantType();
        return GrantType.CLIENT_CREDENTIALS.toString().equalsIgnoreCase(grantType);
    }

    /**
     * 规则验证通过后处理
     * 请求url:/oauth/token?client_id=credentials-client&client_secret=credentials-secret&grant_type=client_credentials&scope=read write
     * 响应{"access_token":"38187f32-e4fb-4650-8e4a-99903b66f20e","token_type":"bearer","expires_in":38000}
     *
     * @throws OAuthSystemException
     */
    @Override
    public synchronized void handleAfterValidation() throws OAuthSystemException {
        String username = tokenRequest.getUsername();
        if (Assert.isBlank(username)) {
            OauthToken oauthToken = oauthService.doAccessTokenByClient(clientDetails(), tokenRequest.getScopes());
            final ReturnBean returnBean = createTokenResponse(oauthToken, false);
            Object rbJson = JSON.toJSONString(returnBean);
            log.debug("客户端模式响应: {}", rbJson);
            ServletUtils.printJson(response, rbJson);
        } else {
            OauthToken oauthToken = oauthService.doAccessTokenByPassword(clientDetails(), tokenRequest.getScopes(), tokenRequest.getUsername());
            final ReturnBean returnBean = createTokenResponse(oauthToken, false);
            Object rbJson = JSON.toJSONString(returnBean);
            log.debug("密码模式响应: {}", rbJson);
            ServletUtils.printJson(response, rbJson);
        }

    }

    @Override
    protected AbstractOauthTokenValidator getValidator() {
        return new ClientCredentialsClientDetailsValidator(tokenRequest);
    }

}
