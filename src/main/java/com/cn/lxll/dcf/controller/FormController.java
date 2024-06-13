package com.cn.lxll.dcf.controller;

import com.alibaba.fastjson2.JSONObject;
import com.cn.lxll.dcf.message.Message;
import com.cn.lxll.dcf.pojo.form.Form;
import com.cn.lxll.dcf.service.FormItemService;
import com.cn.lxll.dcf.service.FormService;
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
@RequestMapping(value = "/form", produces = "application/json;charset=utf-8")
public class FormController {
    @Resource
    private FormService formService;
    @Resource
    private FormItemService formItemService;
    @Resource
    private UserService userService;

    /**
     * 获取表单
     * @param id 表单ID
     * @return 表单
     */
    @ResponseBody
    @GetMapping(value = "/get", params = "id")
    public String getForm(@RequestParam Long id) {
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("form", formService.findFormById(id));
        return jsonObject.toJSONString();
    }

    /**
     * 获取表单控制人
     * @param id 表单ID
     * @return 表单控制人
     */
    @ResponseBody
    @GetMapping(value = "/get/manager", params = "id")
    public String getManager(@RequestParam Long id) {
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("manager", userService.getManagerByFormId(id));
        return jsonObject.toJSONString();
    }

    /**
     * 获取该控制人管理的所有表单
     * @param managerId 控制人ID
     * @return 所有表单
     */
    @ResponseBody
    @GetMapping(value = "/get/all", params = "managerId")
    public String getAllFormsByManager(@RequestParam Long managerId) {
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("forms", formService.getAllFormsByManager(managerId));
        return jsonObject.toJSONString();
    }

    /**
     * 保存表单
     * @param form 表单
     * @return 保存结果
     */
    @PostMapping(value = "/save")
    @ResponseBody
    public String saveForm(@RequestBody Form form) {
        log.info("Save form: {}", form);
        if (form == null) {
            JSONObject jsonObject = Message.FAIL.toJSONObject();
            jsonObject.put("message", "form is null");
            return jsonObject.toJSONString();
        }
        form = formService.save(form);
        if (form == null) {
            JSONObject jsonObject = Message.FAIL.toJSONObject();
            jsonObject.put("message", "form save failed");
            return jsonObject.toJSONString();
        }
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("form", form);
        return jsonObject.toJSONString();
    }

    /**
     * 更新表单
     * @param form 表单
     * @return 更新结果
     */
    @ResponseBody
    @PostMapping(value = "/update")
    public String updateForm(@RequestBody Form form) {
        log.info("Update form: {}", form);
        if (form == null) {
            JSONObject jsonObject = Message.FAIL.toJSONObject();
            jsonObject.put("message", "form is null");
            return jsonObject.toJSONString();
        }
        formService.updateForm(form);
        return Message.SUCCESS.toJSONObject().toJSONString();
    }
    /**
     * 更新表单更新时间
     * @param id 表单ID
     * @return 更新结果
     */
    @ResponseBody
    @PostMapping(value = "/update/updateTime", params = {"id"})
    public String updateUpdateTime(@RequestParam Long id) {
        Form form = new Form();
        form.setId(id);
        formService.updateUpdateTime(form);
        return Message.SUCCESS.toJSONObject().toJSONString();
    }

    /**
     * 删除表单
     * @param id 表单ID
     * @return 删除结果
     */
    @ResponseBody
    @DeleteMapping(value = "/delete", params = "id")
    public String deleteForm(@RequestParam Long id) {
        formService.deleteFormById(id);
        return Message.SUCCESS.toJSONObject().toJSONString();
    }
}
