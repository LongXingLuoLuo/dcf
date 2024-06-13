package com.cn.lxll.dcf.handler;

import com.alibaba.fastjson2.JSONObject;
import com.cn.lxll.dcf.message.Message;
import com.cn.lxll.dcf.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录认证失败处理器
 *
 * @author Lxll
 */
@Log4j2
@Component
public class JwtLoginFailureHandler implements AuthenticationFailureHandler {
    @Resource
    private UserService userService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        httpServletResponse.setContentType("application/json;charset=UTF-8");
//        log.info("登录失败: {} {}", httpServletRequest.getParameter("username"), httpServletRequest.getParameter("password"));
//        User user = userService.getUserByUsername(httpServletRequest.getParameter("username"));
//        log.info("password match: {}", new CustomPasswordEncoder().matches(httpServletRequest.getParameter("password"), user.getPassword()));
        JSONObject jsonObject = Message.FAIL.toJSONObject();
        jsonObject.put("message", "用户名或密码错误");
        httpServletResponse.getWriter().write(jsonObject.toJSONString());
    }
}