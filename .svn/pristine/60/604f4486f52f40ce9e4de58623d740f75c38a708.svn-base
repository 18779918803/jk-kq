package com.jk.xcx.mapper;

import com.jk.common.base.BaseMapper;
import com.jk.xcx.entity.XcxPageRes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 小程序页面资源列表映射层
 *
 * @author 温龙飞
 * @version 1.0
 * @date 2020年07月09日
 */
@Mapper
public interface XcxPageResMapper extends BaseMapper<XcxPageRes> {

    List getWorksRecListByUserId(@Param("uid") Integer uid);
}
