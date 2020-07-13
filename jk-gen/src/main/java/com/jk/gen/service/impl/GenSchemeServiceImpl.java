package com.jk.gen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jk.common.base.BaseServiceImpl;
import com.jk.gen.entity.GenScheme;
import com.jk.gen.mapper.GenSchemeMapper;
import com.jk.gen.service.IGenSchemeService;
import org.springframework.stereotype.Service;

/**
 * 生成方案服务实现层
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2018年05月03日
 */
@Service
public class GenSchemeServiceImpl extends BaseServiceImpl<GenSchemeMapper, GenScheme> implements IGenSchemeService {


    /**
     * 根据tableId删除
     *
     * @param tableId 表格id
     */
    @Override
    public void deleteByTableId(Integer tableId) {
        LambdaQueryWrapper<GenScheme> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GenScheme::getTableId, tableId);
         delete(wrapper);
    }
}
