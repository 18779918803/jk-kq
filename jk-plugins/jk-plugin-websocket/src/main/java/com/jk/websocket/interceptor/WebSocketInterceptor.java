package com.jk.websocket.interceptor;

import com.jk.common.constant.GlobalConstant;
import com.jk.common.validator.Assert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import java.util.Map;

/**
 * WebSocket拦截器
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2018年07月18日
 */
@Slf4j
public class WebSocketInterceptor implements HandshakeInterceptor {


    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        if (serverHttpRequest instanceof ServletServerHttpRequest) {

            String userId = ((ServletServerHttpRequest) serverHttpRequest).getServletRequest().getParameter("userId");
            if (Assert.isBlank(userId)) {
                return false;
            } else {
                log.debug("-----当前session的ID:{}--------",userId);
                map.put(GlobalConstant.SESSION_USER, userId);
            }
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
        log.debug("--------进入webSocket的afterHandshake拦截器！----------");
    }
}

