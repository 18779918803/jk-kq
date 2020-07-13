package com.jk.api.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.jk.api.constant.ApiConst;
import com.jk.api.dto.SessionDTO;
import com.jk.api.service.ApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 微信小程序接口拦截器，主要作用校验请求是否有third_seesion，
 * 同时将头部的third_session转换到request中去
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2020年03月06日
 */
@Slf4j
@Component
public class OpenUserInterceptor implements HandlerInterceptor {

    @Autowired
    private ApiService apiService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // log.info("接口拦截器");
        String thirdSession = request.getHeader(ApiConst.THIRD_SESSION);
        if( thirdSession == null ){
            error(response);
            return false;
        }
        SessionDTO sessionDTO = apiService.selectSession(request, thirdSession);
        if( sessionDTO == null ) {
            error(response);
            return false;
        }
        request.setAttribute(ApiConst.THIRD_SESSION, sessionDTO);
        // log.info("放行");
        return true;
    }

    private void error(HttpServletResponse response) throws IOException {
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        PrintWriter pw = response.getWriter();
        JSONObject retval = new JSONObject();
        retval.put("code", 500);
        retval.put("msg", "无访问权限");
        pw.write(retval.toString());
        pw.flush();
        pw.close();
    }
}
