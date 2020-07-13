package com.jk.common.exception;

import com.alibaba.fastjson.JSON;
import com.jk.common.bean.ReturnBean;
import com.jk.common.bean.ReturnCode;
import com.jk.common.util.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 统一异常处理器
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2019年12月25日
 */
@Slf4j
@Component
public class GlobalExceptionHandler implements HandlerExceptionResolver {

    /**
     * API 地址访问前缀
     */
    private final String API_PRE = "/api/";

    /**
     * 错误页
     */
    public final String ERROR_PATG = "error/500";

    /**
     * 无权限页
     */
    public final String UNAUTH_PATG = "error/403";

    /**
     * 异常解析器
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        try {
            //判断是否是ajax请求
            String path = request.getServletPath();
            if (ServletUtils.isAjax(request) || path.startsWith(API_PRE)) {
                ReturnBean rb;
                if (ex instanceof KrtException) {
                    rb = ((KrtException) ex).getReturnBean();
                    log.error("请求地址:{},程序异常:{}", path, rb.getMsg());
                } else if (ex instanceof UnauthorizedException) {
                    rb = ReturnBean.error(ReturnCode.FORBIDDEN);
                } else {
                    ex.printStackTrace();
                    rb = ReturnBean.error();
                }
                String json = JSON.toJSONString(rb);
                ServletUtils.printJson(response, json);
            } else {
                // 页面请求
                if (ex instanceof UnauthorizedException) {
                    return new ModelAndView(UNAUTH_PATG);
                } else if (ex instanceof UploadException) {
                    ReturnBean rb = ((UploadException) ex).getReturnBean();
                    log.error("请求地址:{},程序异常:{}", path, rb.getMsg());
                    ServletUtils.printText(response, JSON.toJSONString(rb));
                } else {
                    ex.printStackTrace();
                    return new ModelAndView(ERROR_PATG);
                }
            }
        } catch (Exception e) {
            log.error("KrtExceptionHandler 异常处理失败", e);
        }
        return new ModelAndView();
    }
}
