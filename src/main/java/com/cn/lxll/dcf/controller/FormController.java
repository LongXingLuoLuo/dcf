package com.cn.lxll.dcf.controller;

import com.alibaba.fastjson2.JSONObject;
import com.cn.lxll.dcf.message.Message;
import com.cn.lxll.dcf.pojo.User;
import com.cn.lxll.dcf.pojo.form.Form;
import com.cn.lxll.dcf.service.FormItemService;
import com.cn.lxll.dcf.service.FormService;
import com.cn.lxll.dcf.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.Date;

/**
 * Project dcf
 *
 * @author Lxll
 */
@Log4j2
@Controller
@RequestMapping(value = "/form", produces = "application/json;charset=utf-8")
public class FormController {
    @Resource
    private FormService formService;
    @Resource
    private FormItemService formItemService;
    @Resource
    private UserService userService;

    @ResponseBody
    @GetMapping(value = "/get/{id:\\d+}")
    public String getForm(@PathVariable Long id) {
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("form", formService.getFormById(id));
        return jsonObject.toJSONString();
    }

    @ResponseBody
    @GetMapping(value = "/get/{id:\\d+}/manager")
    public String getManager(@PathVariable Long id) {
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("manager", userService.getUserByFormId(id));
        return jsonObject.toJSONString();
    }

    @ResponseBody
    @GetMapping(value = "/get/all")
    public String getAllFormsByManager() {
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("forms", formService.getAllForms());
        return jsonObject.toJSONString();
    }

    @ResponseBody
    @GetMapping(value = "/get/all", params = "managerId")
    public String getAllFormsByManager(@RequestParam Long managerId) {
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("forms", formService.getAllFormsByManager(userService.getUserById(managerId)));
        return jsonObject.toJSONString();
    }

    @ResponseBody
    @GetMapping(value = "/get/all/self")
    public String getAllFormsOfSelf(Principal principal) {
        if (principal == null) {
            JSONObject jsonObject = Message.UNAUTHORIZED.toJSONObject();
            jsonObject.put("message", "用户未登录");
            log.info("Get self user: user not login");
            return jsonObject.toJSONString();
        }
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("forms", formService.getAllFormsByManager(userService.getUserByUsername(principal.getName())));
        return jsonObject.toJSONString();
    }

    @ResponseBody
    @PostMapping(value = "/add")
    public String addForm(Principal principal) {
        if (principal == null) {
            JSONObject jsonObject = Message.UNAUTHORIZED.toJSONObject();
            jsonObject.put("message", "用户未登录");
            log.info("Get self user: user not login");
            return jsonObject.toJSONString();
        }
        Form form = new Form();
        form.setTitle("表单标题");
        form.setDescription("表单描述");
        form.setManager(userService.getUserByUsername(principal.getName()));
        form = formService.addForm(form);
        if (form != null) {
            JSONObject jsonObject = Message.SUCCESS.toJSONObject();
            jsonObject.put("form", form);
            return jsonObject.toJSONString();
        } else {
            JSONObject jsonObject = Message.FAIL.toJSONObject();
            jsonObject.put("message", "添加表单失败");
            return jsonObject.toJSONString();
        }
    }

    @ResponseBody
    @PostMapping(value = "/add", params = "managerId")
    public String addForm(@RequestParam Long managerId) {
        User manager = userService.getUserById(managerId);
        if (managerId == null) {
            JSONObject jsonObject = Message.FAIL.toJSONObject();
            jsonObject.put("message", "管理员不存在");
            return jsonObject.toJSONString();
        }
        Form form = new Form();
        form.setTitle("表单标题");
        form.setDescription("表单描述");
        form.setManager(manager);
        form = formService.addForm(form);
        if (form != null) {
            JSONObject jsonObject = Message.SUCCESS.toJSONObject();
            jsonObject.put("form", form);
            return jsonObject.toJSONString();
        } else {
            JSONObject jsonObject = Message.FAIL.toJSONObject();
            jsonObject.put("message", "添加表单失败");
            return jsonObject.toJSONString();
        }
    }

    @ResponseBody
    @PostMapping(value = "/update")
    public String updateForm(@RequestBody Form form) {
        if (form == null) {
            JSONObject jsonObject = Message.FAIL.toJSONObject();
            jsonObject.put("message", "form is null");
            return jsonObject.toJSONString();
        }
        log.info("Update form: {}", form);
        formService.updateFormWithoutManager(form);
        return Message.SUCCESS.toJSONObject().toJSONString();
    }

    @ResponseBody
    @PostMapping(value = "/update/{id:\\d+}", params = "title")
    public String updateTitle(@PathVariable Long id, @RequestParam String title) {
        if (title == null || title.trim().isEmpty()) {
            JSONObject jsonObject = Message.FAIL.toJSONObject();
            jsonObject.put("message", "标题不能为空");
            return jsonObject.toJSONString();
        }
        Form form = new Form();
        form.setId(id);
        form.setTitle(title.trim());
        log.info("Update form title: {}", form);
        formService.updateTitle(form);
        return Message.SUCCESS.toJSONObject().toJSONString();
    }

    @ResponseBody
    @PostMapping(value = "/update/{id:\\d+}", params = "help")
    public String updateDescription(@PathVariable Long id, @RequestParam String description) {
        if (description == null || description.trim().isEmpty()) {
            JSONObject jsonObject = Message.FAIL.toJSONObject();
            jsonObject.put("message", "描述不能为空");
            return jsonObject.toJSONString();
        }
        Form form = new Form();
        form.setId(id);
        form.setDescription(description.trim());
        formService.updateDescription(form);
        return Message.SUCCESS.toJSONObject().toJSONString();
    }

    @ResponseBody
    @PostMapping(value = "/update/{id:\\d+}", params = "managerId")
    public String updateManager(@PathVariable Long id, @RequestParam Long managerId) {
        Form form = new Form();
        form.setId(id);
        form.setManager(userService.getUserById(managerId));
        formService.updateManager(form);
        return Message.SUCCESS.toJSONObject().toJSONString();
    }

    @ResponseBody
    @PostMapping(value = "/update/{id:\\d+}/updateTime")
    public String updateUpdateTime(@PathVariable Long id) {
        Form form = new Form();
        form.setId(id);
        formService.updateUpdateTime(form);
        return Message.SUCCESS.toJSONObject().toJSONString();
    }

    @ResponseBody
    @GetMapping(value = "/exist/{id:\\d+}")
    public String isFormExist(@PathVariable Long id) {
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("exist", formService.isFormExist(id));
        return jsonObject.toJSONString();
    }

    @ResponseBody
    @DeleteMapping("/delete/{id:\\d+}")
    public String deleteForm(@PathVariable Long id) {
        formService.deleteFormById(id);
        return Message.SUCCESS.toJSONObject().toJSONString();
    }
}
