package com.jk.oauth.authz.validator;

import com.jk.common.bean.ReturnBean;
import com.jk.common.util.SpringUtils;
import com.jk.oauth.entity.ClientDetails;
import com.jk.oauth.service.IOauthService;
import lombok.extern.slf4j.Slf4j;
import org.apache.oltu.oauth2.as.request.OAuthRequest;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

/**
 * 客户端验证器抽象类
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2017年12月09日
 */
@Slf4j
public abstract class AbstractClientDetailsValidator {

    protected IOauthService oauthService = SpringUtils.getBean(IOauthService.class);

    protected OAuthRequest oauthRequest;

    private ClientDetails clientDetails;

    protected AbstractClientDetailsValidator(OAuthRequest oauthRequest) {
        this.oauthRequest = oauthRequest;
    }


    protected ClientDetails clientDetails() {
        if (clientDetails == null) {
            clientDetails = oauthService.selectClientById(oauthRequest.getClientId());
        }
        return clientDetails;
    }

    /**
     * 验证
     *
     * @param clientDetails
     * @return 验证码结果
     * @throws OAuthSystemException
     */
    protected abstract ReturnBean validateSelf(ClientDetails clientDetails) throws OAuthSystemException;


    /**
     * 验证
     *
     * @throws OAuthSystemException
     */
    public final ReturnBean validate() throws OAuthSystemException {
        final ClientDetails details = clientDetails();
        if (details == null) {
            return ReturnBean.error("client_id '" + oauthRequest.getClientId() + "'错误");
        }
        return validateSelf(details);
    }
}
