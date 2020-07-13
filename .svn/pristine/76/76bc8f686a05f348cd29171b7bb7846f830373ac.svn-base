package com.jk.api.controller;

import com.jk.api.service.ApiService;
import com.jk.common.annotation.KrtIgnoreAuth;
import com.jk.common.bean.ReturnBean;
import com.jk.xcx.service.IXcxPageResService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 文件描述：
 *
 * @Author 温龙飞
 * @Date 2020年07月09日
 * @Version 1.0
 */
@RestController
@RequestMapping("api/wechat/xcx")
public class XcxController {

    @Autowired
    private IXcxPageResService xcxPageResService;
    @Autowired
    private ApiService apiService;

    /**
     * 请求工作页面资源接口
     * @return
     */
    @KrtIgnoreAuth
    @GetMapping("worksRecList")
    public ReturnBean getWorksRecList(HttpServletRequest request){
        return xcxPageResService.getWorksRecList(apiService.getOpenUser(request).getUserId());
    }
}
