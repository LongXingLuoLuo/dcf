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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
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
    @Autowired
    private RoleDao roleDao;

    @ResponseBody
    @PostMapping(value = "/register", params = {"username", "password"})
    public String register(@RequestParam String username, @RequestParam String password) {
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
        log.info("Register user: {}, {}", username, password);
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRoles(new ArrayList<>());
        user.getRoles().add(roleDao.findByName("user"));
        user = userService.addUser(user);
        if (user == null){
            JSONObject jsonObject = Message.FAIL.toJSONObject();
            jsonObject.put("message", "用户注册失败");
            return jsonObject.toJSONString();
        } else {
            JSONObject jsonObject = Message.SUCCESS.toJSONObject();
            jsonObject.put("user", user);
            return jsonObject.toJSONString();
        }
    }
}
