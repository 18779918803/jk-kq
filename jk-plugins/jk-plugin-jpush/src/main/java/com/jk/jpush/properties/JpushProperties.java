package com.jk.jpush.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 推送配置
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2018年07月11日
 */
@Data
@Component
@ConfigurationProperties(prefix = "jk.jpush")
public class JpushProperties {

    /**
     * 极光推送secret
     */
    private String secret;

    /**
     * 极光推送appkey
     */
    private String appkey;
}
