package com.jk.framework.config;

import com.jk.framework.handler.CustomRequestMappingHandlerMapping;
import com.jk.framework.interceptor.AuthorizationInterceptor;
import com.jk.framework.interceptor.LogInterceptor;
import com.jk.framework.resolver.LoginUserHandlerMethodArgumentResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.List;


/**
 * MVC配置
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2017年04月15日
 */
@Slf4j
@Configuration
public class WebMvcConfig implements WebMvcConfigurer, WebMvcRegistrations {


    @Autowired
    private LogInterceptor logInterceptor;


    @Autowired
    private AuthorizationInterceptor authorizationInterceptor;

    @Autowired
    private LoginUserHandlerMethodArgumentResolver loginUserHandlerMethodArgumentResolver;

    @Value("${web.update-path}")
    private String updatePath;

    /**
     * 配置拦截器
     *
     * @param registry 拦截器注册器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/dist/**")
                .excludePathPatterns("/html/**")
                .excludePathPatterns("/plugin/**")
                .excludePathPatterns("/file/**")
                .excludePathPatterns("/webjars/**")
                .excludePathPatterns("/swagger-resources/**")
                .excludePathPatterns("/ws/**");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/api/**");
    }

    /**
     * 添加自定义解析器
     *
     * @param argumentResolvers 解析器
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(loginUserHandlerMethodArgumentResolver);
    }

    /**
     * 添加静态资源
     *
     * @param registry 资源注册器
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.debug("文件上传路径:{}", updatePath);
        registry.addResourceHandler("/file/**").addResourceLocations("file:" + updatePath);
    }

    /**
     * 首页跳转
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/index");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return new CustomRequestMappingHandlerMapping();
    }
}
