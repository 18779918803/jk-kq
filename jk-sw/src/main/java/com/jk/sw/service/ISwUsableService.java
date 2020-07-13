package com.jk.sw.service;

import com.jk.common.base.IBaseService;
import com.jk.sw.entity.SwUsable;

import java.util.Collection;


/**
 * 文件可用范围服务接口层
 *
 * @author 温龙飞
 * @version 1.0
 * @date 2020年06月16日
 */
public interface ISwUsableService extends IBaseService<SwUsable> {

    boolean insertBatch(Collection<SwUsable> entityList, int batchSize);
}
