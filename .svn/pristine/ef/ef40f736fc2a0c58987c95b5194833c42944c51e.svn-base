package com.jk.sys.service;

import com.jk.sys.entity.Number;
import com.jk.common.base.IBaseService;

/**
 * 流水号服务接口层
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2019年12月25日
 */
public interface INumberService extends IBaseService<Number> {

    /**
     * 生成流水号
     *
     * @param code 通过数据库行级锁获取流水号
     * @return {@link String} 流水号
     */
    String createNum(String code);

}
