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
import java.util.List;

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

    @ResponseBody
    @GetMapping("/get/{id:\\d+}")
    public String getFormItem(@PathVariable Long id) {
        if (id == null) {
            JSONObject jsonObject = Message.FAIL.toJSONObject();
            jsonObject.put("message", "id is null");
            return jsonObject.toJSONString();
        }
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("formItem", formItemService.getFormItemById(id));
        return jsonObject.toJSONString();
    }

    @ResponseBody
    @GetMapping("/get/all")
    public String getAllFormItems() {
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("formItems", formItemService.getAllFormItems());
        log.info("Get all formItems");
        return jsonObject.toJSONString();
    }

    @ResponseBody
    @GetMapping(value = "/get/all", params = "formId")
    public String getAllFormItemsOfForm(@RequestParam Long formId) {
        if (formId == null) {
            JSONObject jsonObject = Message.FAIL.toJSONObject();
            jsonObject.put("message", "formId is null");
            return jsonObject.toJSONString();
        }
        Form form = new Form();
        form.setId(formId);
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("formItems", formItemService.getAllFormItemsByForm(form));
        log.info("Get all formItems of form: {}", formId);
        return jsonObject.toJSONString();
    }

    @ResponseBody
    @PostMapping(value = "/update")
    public String updateFormItem(@RequestBody FormItem formItem) {
        if (formItem == null) {
            JSONObject jsonObject = Message.FAIL.toJSONObject();
            jsonObject.put("message", "formItem is null");
            return jsonObject.toJSONString();
        }
        log.info("Update formItem: {}", formItem);
        formItemService.updateFormItemWithoutForm(formItem);
        return Message.SUCCESS.toJSONObject().toJSONString();
    }

    @ResponseBody
    @PostMapping(value = "/update/{id:\\d+}/label")
    public String updateTitle(@PathVariable Long id, @RequestBody String label) {
        if (id == null || label == null) {
            JSONObject jsonObject = Message.FAIL.toJSONObject();
            jsonObject.put("message", "id or content is null");
            return jsonObject.toJSONString();
        }
        FormItem formItem = new FormItem();
        formItem.setId(id);
        formItem.setLabel(label);
        formItemService.updateLabel(formItem);
        return Message.SUCCESS.toJSONObject().toJSONString();
    }

    @ResponseBody
    @PostMapping(value = "/update/{id:\\d+}/help")
    public String updateHelp(@PathVariable Long id, @RequestBody String help) {
        if (id == null) {
            JSONObject jsonObject = Message.FAIL.toJSONObject();
            jsonObject.put("message", "id or content is null");
            return jsonObject.toJSONString();
        }
        FormItem formItem = new FormItem();
        formItem.setId(id);
        formItem.setHelp(help);
        formItemService.updateLabel(formItem);
        return Message.SUCCESS.toJSONObject().toJSONString();
    }

    @ResponseBody
    @PostMapping(value = "/update/{id:\\d+}/type")
    public String updateType(@PathVariable Long id, @RequestBody String type) {
        if (id == null || type == null || type.trim().isEmpty()) {
            JSONObject jsonObject = Message.FAIL.toJSONObject();
            jsonObject.put("message", "id or content is null");
            return jsonObject.toJSONString();
        }
        FormItem formItem = new FormItem();
        formItem.setId(id);
        formItem.setType(type);
        formItemService.updateLabel(formItem);
        return Message.SUCCESS.toJSONObject().toJSONString();
    }

    @ResponseBody
    @PostMapping(value = "/update/{id:\\d+}/required")
    public String updateRequired(@PathVariable Long id, @RequestBody Boolean required) {
        if (id == null) {
            JSONObject jsonObject = Message.FAIL.toJSONObject();
            jsonObject.put("message", "id or content is null");
            return jsonObject.toJSONString();
        }
        FormItem formItem = new FormItem();
        formItem.setId(id);
        formItem.setRequired(required);
        formItemService.updateLabel(formItem);
        return Message.SUCCESS.toJSONObject().toJSONString();
    }

    @ResponseBody
    @PostMapping(value = "/update/{id:\\d+}/options")
    public String updateOptions(@PathVariable Long id, @RequestBody List<String> options) {
        if (id == null) {
            JSONObject jsonObject = Message.FAIL.toJSONObject();
            jsonObject.put("message", "id or content is null");
            return jsonObject.toJSONString();
        }
        FormItem formItem = new FormItem();
        formItem.setId(id);
        formItem.setOptions(options);
        formItemService.updateLabel(formItem);
        return Message.SUCCESS.toJSONObject().toJSONString();
    }

    // add
//    @ResponseBody
//    @PostMapping(value = "/add", params = "formId")
//    public String addFormItem(@RequestParam Integer formId) {
//        if (formId == null) {
//            JSONObject jsonObject = Message.FAIL.toJSONObject();
//            jsonObject.put("message", "formId 为空");
//            return jsonObject.toJSONString();
//        }
//        Form form = new Form();
//        form.setId(Long.valueOf(formId));
//        FormItem formItem = new FormItem();
//        formItem.setHelp("表单项描述");
//        formItem.setLabel("表单项标题");
//        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
//        jsonObject.put("formItem", formItemService.addFormItem(formItem, form));
//        return jsonObject.toJSONString();
//    }

    @ResponseBody
    @PostMapping(value = "/add")
    public String addFormItem(@RequestBody JSONObject jsonParam) {
        Long formId = jsonParam.getLong("formId");
        FormItem formItem = jsonParam.getObject("formItem", FormItem.class);
        if (formId == null) {
            JSONObject jsonObject = Message.FAIL.toJSONObject();
            jsonObject.put("message", "formId 为空");
            return jsonObject.toJSONString();
        }
        Form form = new Form();
        form.setId(formId);
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("formItem", formItemService.addFormItem(formItem, form));
        return jsonObject.toJSONString();
    }

    @ResponseBody
    @DeleteMapping("/delete/{id:\\d+}")
    public String deleteFormItem(@PathVariable Long id) {
        formItemService.deleteFormItemById(id);
        return Message.SUCCESS.toJSONObject().toJSONString();
    }
}
