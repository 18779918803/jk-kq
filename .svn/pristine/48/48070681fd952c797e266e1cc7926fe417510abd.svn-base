package com.jk.sys.mapper;

import com.jk.sys.entity.Region;
import com.jk.common.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 区域映射层
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2019年12月25日
 */
@Mapper
public interface RegionMapper extends BaseMapper<Region> {

    /**
     * 根据pid查询区域信息
     *
     * @param pid 区域pid
     * @return 区域信息
     */
    List<Region> selectByPid(Integer pid);
}
