package com.cn.lxll.dcf.controller;

import com.alibaba.fastjson2.JSONObject;
import com.cn.lxll.dcf.message.Message;
import com.cn.lxll.dcf.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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

    /**
     * 获取所有用户
     * @return 所有用户
     */
    @GetMapping(value ="/get/all")
    @ResponseBody
    public String getAllUsers() {
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("users", userService.getAllUsers());
        log.info("Get all users");
        return jsonObject.toJSONString();
    }

    /**
     * 根据id获取用户
     * @param id 用户id
     * @return 用户
     */
    @GetMapping(value ="/get", params = "id")
    @ResponseBody
    public String getUserById(@RequestParam("id") Long id) {
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("user", userService.getUserById(id));
        log.info("Get user by id: {}", id);
        return jsonObject.toJSONString();
    }

    /**
     * 根据用户名获取用户
     * @param username 用户名
     * @return 用户
     */
    @GetMapping(value ="/get", params = "username")
    @ResponseBody
    public String getUserByUsername(@RequestParam("username") String username) {
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("user", userService.getUserByUsername(username));
        log.info("Get user by username: {}", username);
        return jsonObject.toJSONString();
    }

    /**
     * 更新用户名
     * @param userId 用户id
     * @param username 用户名
     * @return 更新后的用户
     */
    @PostMapping(value ="/update/username", params = {"userId", "username"})
    @ResponseBody
    public String updateUsername(@RequestParam("userId") Long userId, @RequestParam("username") String username) {
        userService.updateUsername(userId, username);
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("user", userService.getUserById(userId));
        return jsonObject.toJSONString();
    }

    /**
     * 更新密码
     * @param userId 用户id
     * @param password 密码
     * @return 更新后的用户
     */
    @PostMapping(value ="/update/password", params = {"userId", "password"})
    @ResponseBody
    public String updatePassword(@RequestParam("userId") Long userId, @RequestParam("password") String password) {
        userService.updatePassword(userId, password);
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("user", userService.getUserById(userId));
        return jsonObject.toJSONString();
    }

    /**
     * 添加用户角色到用户
     * @param userId 用户id
     * @param roleId 角色id
     * @return 添加角色后的用户
     */
    @PostMapping(value ="/update/roles/add", params = {"userId", "roleId"})
    @ResponseBody
    public String updateRolesAdd(@RequestParam("userId") Long userId, @RequestParam("roleId") Long roleId) {
        userService.addRole(userId, roleId);
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("user", userService.getUserById(userId));
        return jsonObject.toJSONString();
    }

    /**
     * 移除用户的用户角色
     * @param userId 用户id
     * @param roleId 角色id
     * @return 移除角色后的用户
     */
    @PostMapping(value ="/update/roles/remove", params = {"userId", "roleId"})
    @ResponseBody
    public String updateRolesRemove(@RequestParam("userId") Long userId, @RequestParam("roleId") Long roleId) {
        userService.removeRole(userId, roleId);
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("user", userService.getUserById(userId));
        return jsonObject.toJSONString();
    }

    /**
     * 删除用户
     * @param id 用户id
     * @return 删除结果
     */
    @DeleteMapping(value ="/delete", params = "id")
    @ResponseBody
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteUserById(id);
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        return jsonObject.toJSONString();
    }
}
