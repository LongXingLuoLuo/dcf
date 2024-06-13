package com.cn.lxll.dcf.controller;

import com.alibaba.fastjson2.JSONObject;
import com.cn.lxll.dcf.message.Message;
import com.cn.lxll.dcf.pojo.form.Form;
import com.cn.lxll.dcf.pojo.form.FormItem;
import com.cn.lxll.dcf.service.FormItemService;
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
@RequestMapping(value = "/form/item", produces = "application/json;charset=utf-8")
@Controller
public class FormItemController {
    @Resource
    private FormItemService formItemService;

    /**
     * 获取所有的表单项
     * @return 所有的表单项
     */
    @ResponseBody
    @GetMapping(value = "/get/all")
    public String getAllFormItems() {
        log.info("Get all formItems");
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("formItems", formItemService.findAll());
        return jsonObject.toJSONString();
    }

    /**
     * 获取表单的所有表单项
     * @param formId 表单id
     * @return 表单的所有表单项
     */
    @ResponseBody
    @GetMapping(value = "/get/all", params = "formId")
    public String getAllFormItemsByForm(@RequestParam Long formId) {
        log.info("Get all formItems of form: {}", formId);
        if (formId == null) {
            JSONObject jsonObject = Message.FAIL.toJSONObject();
            jsonObject.put("message", "formId is null");
            return jsonObject.toJSONString();
        }
        Form form = new Form();
        form.setId(formId);
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("formItems", formItemService.findAllByForm(form.getId()));
        log.info("Get all formItems of form: {}", formId);
        return jsonObject.toJSONString();
    }

    /**
     * 根据id获取表单项
     * @param id 表单项id
     * @return 表单项
     */
    @ResponseBody
    @GetMapping(value = "/get", params = "id")
    public String getFormItemById(@RequestParam Long id) {
        log.info("Get formItem: {}", id);
        if (id == null) {
            JSONObject jsonObject = Message.FAIL.toJSONObject();
            jsonObject.put("message", "id is null");
            return jsonObject.toJSONString();
        }
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("formItem", formItemService.getFormItemById(id));
        return jsonObject.toJSONString();
    }

    /**
     * 新增表单项
     * @param formItem 表单项
     * @return 新增的表单项
     */
    @ResponseBody
    @PostMapping(value = "/save")
    public String saveFormItem(@RequestBody FormItem formItem) {
        log.info("Save formItem: {}", formItem);
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("formItem", formItemService.save(formItem));
        return jsonObject.toJSONString();
    }

    /**
     * 更新表单项
     * @param formItem 表单项
     * @return 更新的表单项
     */
    @ResponseBody
    @PostMapping(value = "/update")
    public String updateFormItem(@RequestBody FormItem formItem) {
        log.info("Update formItem: {}", formItem);
        if (formItem == null) {
            JSONObject jsonObject = Message.FAIL.toJSONObject();
            jsonObject.put("message", "formItem is null");
            return jsonObject.toJSONString();
        }
        formItemService.update(formItem);
        return Message.SUCCESS.toJSONObject().toJSONString();
    }

    /**
     * 删除表单项
     * @param id 表单项id
     * @return 删除结果
     */
    @ResponseBody
    @DeleteMapping(value = "/delete", params = "id")
    public String deleteFormItem(@RequestParam Long id) {
        log.info("Delete formItem: {}", id);
        formItemService.deleteById(id);
        return Message.SUCCESS.toJSONObject().toJSONString();
    }
}
