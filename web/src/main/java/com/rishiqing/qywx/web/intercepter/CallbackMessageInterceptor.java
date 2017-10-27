package com.rishiqing.qywx.web.intercepter;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 用来解析回调请求中的加密信息的interceptor，需要在CallbackVerifyInterceptor之后执行
 */
public class CallbackMessageInterceptor extends HandlerInterceptorAdapter {
}
