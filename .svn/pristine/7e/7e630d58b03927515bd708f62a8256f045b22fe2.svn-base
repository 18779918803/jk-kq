package com.jk.api.controller;


import com.jk.api.util.SendWeChatUtil;
import com.jk.common.annotation.KrtIgnoreAuth;
import com.jk.common.base.BaseController;
import com.jk.common.bean.ReturnBean;
import com.jk.wxpush.service.impl.WxPushServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * 请假记录相关的微信小程序API接口
 *
 * @author 何任鹏
 * @version 1.0
 * @date 2020年06月01日
 */
@RestController
@RequestMapping("api/send")
@Slf4j
public class SendApi extends BaseController {


    @Autowired
    private SendWeChatUtil util;



    /**
     * 新增用户请假申请
     * @return
     */
    @KrtIgnoreAuth
    @PostMapping("/up")
    @Transactional
    public ReturnBean up(HttpServletRequest request,String accessToken) throws UnsupportedEncodingException {
        log.error("执行任务,accessToken:{}",accessToken);
        util.getGZHOpenUserId(accessToken);
        return ReturnBean.ok();
    }
}
