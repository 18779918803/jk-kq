package com.jk.sys.service;

import com.jk.sys.entity.DicType;
import com.jk.common.base.IBaseService;

/**
 * 字典类型表服务接口层
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2019年12月25日
 */
public interface IDicTypeService extends IBaseService<DicType> {

    /**
     * 删除字典类型
     *
     * @param id   字典类型id
     * @param code 字典类型编码
     */
    void deleteByIdAndCode(Integer id, String code);

    /**
     * 批量删除字典类型
     *
     * @param ids   字典类型id
     * @param codes 字典类型编码s
     */
    void deleteByIdsAndCodes(Integer[] ids, String[] codes);
}
