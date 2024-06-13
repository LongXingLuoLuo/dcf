package com.cn.lxll.dcf.config;

import com.cn.lxll.dcf.handler.*;
import com.cn.lxll.dcf.filter.*;
import com.cn.lxll.dcf.service.UserService;
import com.cn.lxll.dcf.util.CustomPasswordEncoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

/**
 * Project dcf
 *
 * @author 龙星洛洛
 */
@Log4j2
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserService userService;

    @Resource
    private JwtLoginFailureHandler loginFailureHandler;

    @Resource
    private JwtLoginSuccessHandler loginSuccessHandler;

    @Resource
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Resource
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Resource
    private JwtLogoutSuccessHandler jwtLogoutSuccessHandler;

    @Resource
    private CustomCorsConfigurationSource corsConfigurationSource;

    @Resource
    private JwtLoginSuccessHandler jwtLoginSuccessHandler;

    /**
     * 密码加密方式
     * 选择消除密码方式加密
     *
     * @return 密码加密方式 password encoder
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new CustomPasswordEncoder();
    }

    /**
     * JWT过滤器
     * @return JWT过滤器
     * @throws Exception 异常
     */
    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        return new JwtAuthenticationFilter(authenticationManager());
    }

    /**
     * http 访问设置
     *
     * @param http http 访问请求
     * @throws Exception 错误
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/auth/login")
                .successHandler(jwtLoginSuccessHandler)
                .failureHandler(loginFailureHandler)
            .and().logout()
                .logoutUrl("/auth/logout")
                .logoutSuccessHandler(jwtLogoutSuccessHandler)
            .and().cors()
                .configurationSource(corsConfigurationSource)
                .and()
            .csrf()
                .disable()//关闭csrf
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)    //不通过session获取securityContext
            .and().authorizeRequests()
                .antMatchers("/auth/login", "/auth/logout").permitAll()
                .anyRequest().permitAll()
            .and().exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
            .and()
                .addFilter(jwtAuthenticationFilter());
    }

    /**
     * 角色继承
     * 实现所有 user 能够访问的资源，admin 都能够访问
     *
     * @return 角色层次 role hierarchy
     */
    @Bean
    RoleHierarchy roleHierarchy() {
        //admin 权限大于 user , 使admin可以访问user的内容
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy("ROLE_admin > ROLE_user");
        return hierarchy;
    }

    /**
     * 账号配置
     * 链接自己的 userService
     *
     * @param auth auth 访问
     * @throws Exception 可能的报错
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // admin 账号拥有 "admin" 权限
//        auth.inMemoryAuthentication().withUser("admin").password(new BCryptPasswordEncoder().encode("111111")).roles("admin");
        auth.userDetailsService(userService);
    }
}
