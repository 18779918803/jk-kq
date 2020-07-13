package com.jk.common.base;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
import java.util.Map;

/**
 * 公共抽取的映射层
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2016年7月16日
 */
public interface BaseMapper<T> extends com.baomidou.mybatisplus.core.mapper.BaseMapper<T> {

    /**
     * 自定义<T>分页(PageHelper 模式)
     *
     * @param para
     * @return {@link List}
     */
    List selectPageList(Map para);

    /**
     * 自定义Map分页（PageHelper模式）
     *
     * @param para
     * @return {@link IPage}
     */
    List selectPageMap(Map para);

    /**
     * excel导出
     *
     * @param para
     * @return {@link List}
     */
    List selectExcelList(Map para);
}
