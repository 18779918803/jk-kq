package com.jk.common.shiro;

import org.apache.shiro.util.ByteSource;

/**
 * 解决：
 * shiro 使用缓存时出现：java.io.NotSerializableException: org.apache.shiro.util.SimpleByteSource
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2019年12月25日
 */
public class ByteSourceUtils {

    public static ByteSource bytes(byte[] bytes) {
        return new SimpleByteSource(bytes);
    }

    public static ByteSource bytes(String arg0) {
        return new SimpleByteSource(arg0.getBytes());
    }

}
