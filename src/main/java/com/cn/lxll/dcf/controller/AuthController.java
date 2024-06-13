package com.cn.lxll.dcf.controller;

import com.alibaba.fastjson2.JSONObject;
import com.cn.lxll.dcf.dao.RoleDao;
import com.cn.lxll.dcf.message.Message;
import com.cn.lxll.dcf.pojo.Role;
import com.cn.lxll.dcf.pojo.User;
import com.cn.lxll.dcf.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * Project dcf
 *
 * @author Lxll
 */
@Log4j2
@RequestMapping(value = "/auth", produces = "application/json;charset=utf-8")
@Controller
public class AuthController {
    @Resource
    UserService userService;

    @Resource
    private RoleDao roleDao;

    /**
     * 用户注册
     * @param username 用户名
     * @param password 密码
     * @return 注册结果
     */
    @ResponseBody
    @PostMapping(value = "/register", params = {"username", "password"})
    public String register(@RequestParam String username, @RequestParam String password) {
        log.info("Register user: {}, {}", username, password);
        if (username == null || password == null) {
            JSONObject jsonObject = Message.FAIL.toJSONObject();
            jsonObject.put("message", "用户名或密码为空");
            return jsonObject.toJSONString();
        }
        if (userService.existsByUsername(username)) {
            JSONObject jsonObject = Message.FAIL.toJSONObject();
            jsonObject.put("message", "用户名已存在");
            return jsonObject.toJSONString();
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        List<Role> roles = new ArrayList<>();
        roles.add(roleDao.findUser());
        user = userService.saveWithRoles(user, roles);
        JSONObject jsonObject;
        if (user == null){
            jsonObject = Message.FAIL.toJSONObject();
            jsonObject.put("message", "用户注册失败");
        } else {
            jsonObject = Message.SUCCESS.toJSONObject();
            jsonObject.put("user", user);
        }
        return jsonObject.toJSONString();
    }

    /**
     * 获取当前登录用户
     * @param principal 当前登录用户
     * @return 当前登录用户
     */
    @GetMapping(value ="/self")
    @ResponseBody
    public String getSelf(Principal principal) {
        if (principal == null) {
            JSONObject jsonObject = Message.UNAUTHORIZED.toJSONObject();
            jsonObject.put("message", "用户未登录");
            log.info("Get self user: user not login");
            return jsonObject.toJSONString();
        }
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("user", userService.getUserByUsername(principal.getName()));
        log.info("Get auth self: {}", principal.getName());
        return jsonObject.toJSONString();
    }
}
