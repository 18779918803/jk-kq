package com.jk.sys.controller;

import com.jk.common.base.BaseController;
import com.jk.common.session.SessionUser;
import com.jk.common.util.ShiroUtils;
import com.jk.common.util.DateUtils;
import com.jk.sys.entity.Res;
import com.jk.sys.service.IResService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * 后台首页控制层
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2019年12月25日
 */
@Slf4j
@Controller
public class IndexController extends BaseController {

    @Autowired
    private IResService resService;

    @Value("${jk.version}")
    private String version;

    /**
     * 后台管理页
     */
    @GetMapping("index")
    public String index() {
        SessionUser sessionUser = ShiroUtils.getSessionUser();
        log.debug("当前登录用户:sessionUser:{}", sessionUser);
        List<Res> resList = resService.selectUserUrlRes(sessionUser);
        request.setAttribute("resList", resList);
        request.setAttribute("version", version);
        request.setAttribute("year", DateUtils.getYear());
        return "index";
    }

    /**
     * 欢迎页
     */
    @GetMapping("welcome")
    public String welcome() {
        return "welcome";
    }

}
