package com.jk.xcx.service;


import com.jk.common.base.IBaseService;
import com.jk.common.bean.ReturnBean;
import com.jk.xcx.entity.XcxPageRes;

/**
 * 小程序页面资源列表服务接口层
 *
 * @author 温龙飞
 * @version 1.0
 * @date 2020年07月09日
 */
public interface IXcxPageResService extends IBaseService<XcxPageRes> {

    ReturnBean getWorksRecList(Integer uid);
}
