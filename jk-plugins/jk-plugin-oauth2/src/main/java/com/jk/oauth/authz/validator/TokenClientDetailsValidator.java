package com.jk.oauth.authz.validator;

import com.jk.common.bean.ReturnBean;
import com.jk.oauth.entity.ClientDetails;
import lombok.extern.slf4j.Slf4j;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

/**
 * response_type = 'code'规则验证
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2017年12月09日
 */
@Slf4j
public class TokenClientDetailsValidator extends AbstractClientDetailsValidator {

    private final boolean validateClientSecret;

    public TokenClientDetailsValidator(OAuthAuthzRequest oauthRequest) {
        this(oauthRequest, true);
    }

    public TokenClientDetailsValidator(OAuthAuthzRequest oauthRequest, boolean validateClientSecret) {
        super(oauthRequest);
        this.validateClientSecret = validateClientSecret;
    }

    /**
     * 规则验证
     * grant_type="implicit"   -> response_type="token"
     * ?response_type=token&client_id=[client_id]&client_secret=[client_secret]&redirect_uri=[redirect_uri]
     *
     * @param clientDetails
     * @throws OAuthSystemException
     */
    @Override
    public ReturnBean validateSelf(ClientDetails clientDetails) throws OAuthSystemException {

        //validate client_secret
        if (this.validateClientSecret) {
            final String clientSecret = oauthRequest.getClientSecret();
            if (clientSecret == null || !clientSecret.equals(clientDetails.getClientSecret())) {
                return ReturnBean.error("client_secret错误");
            }
        }

        //validate redirect_uri
        final String redirectURI = oauthRequest.getRedirectURI();
        if (redirectURI == null || !redirectURI.equals(clientDetails.getRedirectUri())) {
            log.debug("Invalid redirect_uri '{}' by response_type = 'code', client_id = '{}'", redirectURI, clientDetails.getClientId());
            return ReturnBean.error("redirect_uri错误");
        }

        return null;
    }


}
