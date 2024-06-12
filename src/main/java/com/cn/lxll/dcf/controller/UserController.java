package com.cn.lxll.dcf.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.cn.lxll.dcf.message.Message;
import com.cn.lxll.dcf.pojo.User;
import com.cn.lxll.dcf.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Principal;

/**
 * Project dcf
 *
 * @author Lxll
 */
@Log4j2
@Controller
@RequestMapping(value = "/user", produces = "application/json;charset=utf-8")
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping(value ="/get/{id:\\d+}")
    @ResponseBody
    public String getUserById(@PathVariable("id") Long id) {
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("user", userService.getUserById(id));
        log.info("Get user by id: {}", id);
        return jsonObject.toJSONString();
    }

    @GetMapping(value ="/get/all")
    @ResponseBody
    public String getAllUsers() {
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("users", userService.getAllUsers());
        log.info("Get all users");
        return jsonObject.toJSONString();
    }

    @GetMapping(value ="/get")
    @ResponseBody
    public String getUserByUsername(@RequestParam("username") String username) {
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("user", userService.getUserByUsername(username));
        log.info("Get user by username: {}", username);
        return jsonObject.toJSONString();
    }

    @GetMapping(value ="/get/self")
    @ResponseBody
    public String getSelfUser(Principal principal) {
        if (principal == null) {
            JSONObject jsonObject = Message.UNAUTHORIZED.toJSONObject();
            jsonObject.put("message", "用户未登录");
            log.info("Get self user: user not login");
            return jsonObject.toJSONString();
        }
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("user", userService.getUserByUsername(principal.getName()));
        log.info("Get self user: {}", principal.getName());
        return jsonObject.toJSONString();
    }

//    @PostMapping(value ="/add")
//    @ResponseBody
//    public String addUser(@RequestBody JSONObject jsonParam) {
//        String username = (String) jsonParam.get("username");
//        String password = (String) jsonParam.get("password");
//        if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
//            JSONObject jsonObject = Message.FAIL.toJSONObject();
//            jsonObject.put("message", "用户名或密码不能为空");
//            return jsonObject.toJSONString();
//        } else {
//            User user = userService.addUser(username, password);
//            if (user == null) {
//                JSONObject jsonObject = Message.FAIL.toJSONObject();
//                jsonObject.put("message", "用户名已存在");
//                return jsonObject.toJSONString();
//            } else {
//                JSONObject jsonObject = Message.SUCCESS.toJSONObject();
//                jsonObject.put("user", user);
//                return jsonObject.toJSONString();
//            }
//        }
//    }

    @PutMapping(value ="/update", params = {"userId", "username"})
    @ResponseBody
    public String updateUsername(@RequestParam("userId") Long id, @RequestParam("username") String username) {
        User user = userService.getUserById(id);
        if (user == null) {
            JSONObject jsonObject = Message.FAIL.toJSONObject();
            jsonObject.put("message", "用户不存在");
            return jsonObject.toJSONString();
        }
        if (username == null || username.trim().isEmpty()) {
            JSONObject jsonObject = Message.FAIL.toJSONObject();
            jsonObject.put("message", "用户名不能为空");
            return jsonObject.toJSONString();
        }
        if (userService.existsByUsername(username)) {
            JSONObject jsonObject = Message.FAIL.toJSONObject();
            jsonObject.put("message", "用户名已存在");
            return jsonObject.toJSONString();
        }
        user.setUsername(username);
        userService.updateUsername(user);
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("user", user);
        return jsonObject.toJSONString();
    }

    @DeleteMapping(value ="/delete/{id:\\d+}")
    @ResponseBody
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        return jsonObject.toJSONString();
    }
}
