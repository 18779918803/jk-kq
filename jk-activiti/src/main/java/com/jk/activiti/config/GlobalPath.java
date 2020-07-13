package com.jk.activiti.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 全局配置类
 * 
 * @author ypl
 */

@Configuration
@Component
public class GlobalPath
{
    /** 路径名称 */

    private static  String path;

    public static  String getPath() {
        return path;
    }
    @Value("${web.path}")
    public  void  setPath(String path) {
        GlobalPath.path = path;
    }
    /**
     * 获取头像上传路径
     */
    public static String getAvatarPath()
    {
        return getPath() + "/avatar";
    }
    /**
     * 获取下载路径
     */
    public static  String getDownloadPath()
    {
        return getPath() + "/download/";
    }
    /**
     * 获取上传路径
     */
    public static  String getUploadPath()
    {
        return getPath() + "/upload";
    }
}
