package com.jk.framework.config;

import com.jagregory.shiro.freemarker.ShiroTags;
import com.jk.framework.tag.DicTag;
import freemarker.template.Configuration;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * freemarker初始化
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2018年06月29日
 */
@org.springframework.context.annotation.Configuration
public class FreemarkerConfig implements InitializingBean {

    @Autowired
    private Configuration configuration;

    @Autowired
    private DicTag dicTag;

    @Override
    public void afterPropertiesSet() throws Exception {
        // shiro标签
        configuration.setSharedVariable("shiro", new ShiroTags());
        //标签名与标签类
        configuration.setSharedVariable("dic", dicTag);
    }

}
