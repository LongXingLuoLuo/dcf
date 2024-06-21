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
     *
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
     *
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
     *
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
     * 获取用户所有可以填写的表单
     *
     * @param userId 用户ID
     * @return 所有可以填写的表单
     */
    @ResponseBody
    @GetMapping(value = "/get/all/visitable", params = "userId")
    public String getAllVisitableForms(@RequestParam Long userId) {
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("forms", formService.findAllVisitableForms(userId));
        return jsonObject.toJSONString();
    }

    /**
     * 保存表单
     *
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
     *
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
     *
     * @param id 表单ID
     * @return 更新结果
     */
    @ResponseBody
    @PostMapping(value = "/update/updateTime", params = {"id"})
    public String updateUpdateTime(@RequestParam Long id) {
        Form form = new Form();
        form.setId(id);
        formService.updateUpdateTime(id);
        return Message.SUCCESS.toJSONObject().toJSONString();
    }

    /**
     * 更新表单更新时间
     *
     * @param formId 表单ID
     * @param refId  引用ID
     * @return 更新结果
     */
    @ResponseBody
    @PostMapping(value = "/update/ref", params = {"formId", "refId"})
    public String updateRef(@RequestParam Long formId, @RequestParam Long refId) {
        log.info("Update form ref: formId={}, refId={}", formId, refId);
        formService.updateRef(formId, refId);
        return Message.SUCCESS.toJSONObject().toJSONString();
    }

    /**
     * 更新表单更新时间
     *
     * @param formId    表单ID
     * @param managerId 控制人ID
     * @return 更新结果
     */
    @ResponseBody
    @PostMapping(value = "/update/manager", params = {"formId", "managerId"})
    public String updateManager(@RequestParam Long formId, @RequestParam Long managerId) {
        formService.updateManager(formId, managerId);
        return Message.SUCCESS.toJSONObject().toJSONString();
    }

    @PostMapping(value = "/update/visited", params = {"userId", "formId"})
    @ResponseBody
    public String updateVisitedForm(@RequestParam Long userId, @RequestParam Long formId) {
        formService.updateVisited(userId, formId);
        return Message.SUCCESS.toJSONObject().toJSONString();
    }

    @GetMapping(value = "/exist/visited", params = {"userId", "formId"})
    @ResponseBody
    public String existVisitedForm(@RequestParam Long userId, @RequestParam Long formId) {
        log.info("existVisitedForm: userId={}, formId={}", userId, formId);
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("exist", formService.isVisitedForm(userId, formId));
        return jsonObject.toJSONString();
    }

    @ResponseBody
    @GetMapping(value = "/count/all/nonVisited", params = "userId")
    public String countAllNonVisitedForms(@RequestParam Long userId) {
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("count", formService.countAllNonVisitForms(userId));
        return jsonObject.toJSONString();
    }

    @ResponseBody
    @GetMapping(value = "/is/visitable", params = {"userId", "formId"})
    public String isVisitable(@RequestParam Long userId, @RequestParam Long formId){
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("is", formService.isVisitable(userId, formId));
        return jsonObject.toJSONString();
    }

    /**
     * 删除表单
     *
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
