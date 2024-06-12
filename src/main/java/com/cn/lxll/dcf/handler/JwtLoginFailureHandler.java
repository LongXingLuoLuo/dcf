package com.cn.lxll.dcf.handler;

import com.alibaba.fastjson2.JSONObject;
import com.cn.lxll.dcf.message.Message;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录认证失败处理器
 *
 * @author Lxll
 */
@Component
public class JwtLoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        JSONObject jsonObject = Message.FAIL.toJSONObject();
        jsonObject.put("message", "用户名或密码错误");
        httpServletResponse.getWriter().write(jsonObject.toJSONString());
    }
}