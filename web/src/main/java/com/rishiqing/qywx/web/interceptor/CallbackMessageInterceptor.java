package com.rishiqing.qywx.web.interceptor;

import com.rishiqing.qywx.web.service.CallbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用来解析回调请求中的加密信息的interceptor，需要在CallbackVerifyInterceptor之后执行
 */
public class CallbackMessageInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private CallbackService callbackService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("request.getParameter:" + request.getParameter("msg_signature"));
        try {
            //  根据echostr参数，判断是否是开启接受消息的接口
            String echoString = request.getParameter("echostr");
            if(echoString != null){

            }else{

            }
        } catch (Exception e) {

        }
        return false;
    }
}
