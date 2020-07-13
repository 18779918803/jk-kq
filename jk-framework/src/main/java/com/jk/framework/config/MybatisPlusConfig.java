package com.jk.framework.config;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.extension.MybatisMapWrapperFactory;
import com.jk.framework.interceptor.DataScopeInterceptor;
import com.jk.framework.interceptor.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Mybatis-plus初始化
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2019年01月02日
 */
@Configuration
public class MybatisPlusConfig {

    /**
     * map 转驼峰
     *
     * @return {@link ConfigurationCustomizer}
     */
    @Bean
    public ConfigurationCustomizer mybatisConfigurationCustomizer() {
        return configuration -> configuration.setObjectWrapperFactory(new MybatisMapWrapperFactory());
    }

    /**
     * 分页拦截器(修改mybatis Plus分页)
     *
     * @return {@link PaginationInterceptor}
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * 数据权限
     *
     * @return {@link DataScopeInterceptor}
     */
    @Bean
    public DataScopeInterceptor dataScopeInterceptor() {
        return new DataScopeInterceptor();
    }


}
