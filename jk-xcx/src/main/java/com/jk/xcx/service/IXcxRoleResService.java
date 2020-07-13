package com.jk.xcx.service;

import com.jk.common.base.IBaseService;
import com.jk.common.bean.ReturnBean;
import com.jk.xcx.entity.XcxRoleRes;

import java.util.List;

/**
 * 小程序页面角色关系表服务接口层
 *
 * @author 温龙飞
 * @version 1.0
 * @date 2020年07月09日
 */
public interface IXcxRoleResService extends IBaseService<XcxRoleRes> {

   ReturnBean insertBatch(String resIds, Integer roleId);

   ReturnBean getResByRoleId(Integer roleId);

}
