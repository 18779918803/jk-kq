package com.jk.kq.config;

import com.github.dozermapper.spring.DozerBeanMapperFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;

/**
 * {{文件描述}}
 * * @author 张威伦
 *
 * @version 1.0
 * @date 2020年06月02日
 */
@Configuration//注意此类需要被扫描到
public class DozerConfig {
    @Bean//resources需要包含上述xml
    public DozerBeanMapperFactoryBean dozerMapper(@Value("classpath:dozer.xml") Resource[] resources) throws IOException {
        DozerBeanMapperFactoryBean dozerBeanMapperFactoryBean = new DozerBeanMapperFactoryBean();
        dozerBeanMapperFactoryBean.setMappingFiles(resources);
        return dozerBeanMapperFactoryBean;
    }
}