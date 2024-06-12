package com.cn.lxll.dcf.handler;

import com.alibaba.fastjson2.JSONObject;
import com.cn.lxll.dcf.jwt.JwtUtils;
import com.cn.lxll.dcf.message.Message;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录认证成功处理器
 *
 * @author Lxll
 */
@Component
public class JwtLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        // 生成JWT，并放置到请求头中
        String jwt = JwtUtils.genAccessToken(authentication.getName());
        httpServletResponse.setHeader(JwtUtils.header, jwt);
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        httpServletResponse.getWriter().write(jsonObject.toJSONString());
    }
}