package com.jk.redis.cache;

import org.springframework.data.redis.core.RedisTemplate;

/**
 * redis操作 处理:shiro session 需要用jdk序列化 否则报异常
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2018年09月14日
 */
public class KrtRedisTemplate<K, V> extends RedisTemplate<K, V>{

}
