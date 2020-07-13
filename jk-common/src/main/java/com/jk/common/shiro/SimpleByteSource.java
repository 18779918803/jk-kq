package com.jk.common.shiro;

import java.io.Serializable;

/**
 * 解决：
 * shiro 使用缓存时出现：java.io.NotSerializableException: org.apache.shiro.util.SimpleByteSource
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2019年12月25日
 */

public class SimpleByteSource extends org.apache.shiro.util.SimpleByteSource implements Serializable {

    SimpleByteSource(byte[] bytes) {
        super(bytes);
    }

}
