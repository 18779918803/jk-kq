package com.jk.gen.service;

import com.jk.common.base.IBaseService;
import com.jk.gen.entity.GenScheme;

/**
 * 生成方案服务层
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2018年05月03日
 */
public interface IGenSchemeService extends IBaseService<GenScheme> {


    /**
     * 根据tableId删除
     *
     * @param tableId
     */
    void deleteByTableId(Integer tableId);
}
