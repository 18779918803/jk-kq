package com.jk.api.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * {{文件描述}}
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2020年03月06日
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    OpenUserInterceptor openUserInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(openUserInterceptor)
                .excludePathPatterns("/api/send/*")
                .excludePathPatterns("/api/wechat/auth")
                .excludePathPatterns("/api/wechat/auth/**")
                .addPathPatterns("/api/**");

        

    }
}
