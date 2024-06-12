package com.cn.lxll.dcf.handler;

import com.cn.lxll.dcf.jwt.JwtUtils;
import com.cn.lxll.dcf.message.Message;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登出成功处理器
 *
 * @author Lxll
 */
@Component
public class JwtLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {

        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(httpServletRequest, httpServletResponse, authentication);
        }

        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.setHeader(JwtUtils.header, "");
        httpServletResponse.setHeader("Access-Control-Expose-Headers", JwtUtils.header);
        httpServletResponse.getWriter().write(Message.SUCCESS.toJSONObject().toJSONString());
    }
}
