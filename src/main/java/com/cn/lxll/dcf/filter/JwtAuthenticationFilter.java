package com.cn.lxll.dcf.filter;

import com.cn.lxll.dcf.jwt.JwtUtils;
import com.cn.lxll.dcf.message.Message;
import com.cn.lxll.dcf.pojo.User;
import com.cn.lxll.dcf.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT过滤器JwtAuthenticationFilter
 *
 * @author Lxll
 */
@Log4j2
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    @Resource
    UserService userService;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String jwt = request.getHeader(JwtUtils.header);
        // 这里如果没有jwt，继续往后走，因为后面还有鉴权管理器等去判断是否拥有身份凭证，所以是可以放行的
        // 没有jwt相当于匿名访问，若有一些接口是需要权限的，则不能访问这些接口
        if (jwt == null || jwt.isEmpty()) {
            chain.doFilter(request, response);
            return;
        }

        Claims claim = JwtUtils.parsePayload(jwt);
        if (claim == null) {
            throw new JwtException("token 异常");
        }
        if (JwtUtils.isTokenExpired(claim)) {
            throw new JwtException("token 已过期");
        }

        String username = claim.get("username", String.class);

        // 获取用户的权限等信息
        User user = userService.getUserByUsername(username);
        if (user == null) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(Message.UNAUTHORIZED.toJSONObject().toJSONString());
            return;
        }
        // 构建UsernamePasswordAuthenticationToken,这里密码为null，是因为提供了正确的JWT,实现自动登录
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(token);

        chain.doFilter(request, response);
    }
}
