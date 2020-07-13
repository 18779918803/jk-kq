package com.jk.xcx.service.impl;

import com.jk.common.base.BaseServiceImpl;
import com.jk.common.bean.ReturnBean;
import com.jk.xcx.entity.XcxPageRes;
import com.jk.xcx.mapper.XcxPageResMapper;
import com.jk.xcx.service.IXcxPageResService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


/**
 * 小程序页面资源列表服务接口实现层
 *
 * @author 温龙飞
 * @version 1.0
 * @date 2020年07月09日
 */
@Service
public class XcxPageResServiceImpl extends BaseServiceImpl<XcxPageResMapper, XcxPageRes> implements IXcxPageResService {

    @Autowired
    private XcxPageResMapper xcxPageResMapper;

    @Override
    public ReturnBean getWorksRecList(Integer uid) {
        List list = xcxPageResMapper.getWorksRecListByUserId(uid);
        return ReturnBean.ok(list);
    }
}
