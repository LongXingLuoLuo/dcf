package com.cn.lxll.dcf.controller;

import com.alibaba.fastjson2.JSONObject;
import com.cn.lxll.dcf.message.Message;
import com.cn.lxll.dcf.pojo.model.CustomProperty;
import com.cn.lxll.dcf.service.CustomPropertyService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Project dcf
 * Created on 2024/6/10 上午2:21
 *
 * @author Lxll
 */
@Log4j2
@RequestMapping(value = "/object/property", produces = "application/json;charset=utf-8")
@Controller
public class CustomPropertyController {
    @Resource
    private CustomPropertyService customPropertyService;

    @GetMapping(value = "/get/all")
    @ResponseBody
    public String findAll() {
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("properties", customPropertyService.findAllProperties());
        return jsonObject.toJSONString();
    }

    @GetMapping(value = "/get/all", params = "customObjectId")
    @ResponseBody
    public String findAllByObject(@RequestParam Long customObjectId) {
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("properties", customPropertyService.findAllPropertiesByCustomObject(customObjectId));
        return jsonObject.toJSONString();
    }

    @GetMapping(value = "/get/all", params = "modelId")
    @ResponseBody
    public String findAllByModel(@RequestParam Long modelId) {
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("properties", customPropertyService.findAllPropertiesByModel(modelId));
        return jsonObject.toJSONString();
    }

    @GetMapping(value = "/get", params = "id")
    @ResponseBody
    public String findById(@RequestParam Long id) {
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("property", customPropertyService.findPropertyById(id));
        return jsonObject.toJSONString();
    }

    @GetMapping(value = "/get", params = {"customObjectId", "key"})
    @ResponseBody
    public String findByObjectAndKey(@RequestParam Long customObjectId, @RequestParam String key) {
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("property", customPropertyService.findPropertyByCustomObjectAndKey(customObjectId, key));
        return jsonObject.toJSONString();
    }

    @PostMapping(value = "/add", params = {"customObjectId", "customPropertyId"})
    @ResponseBody
    public String addProperty(@RequestParam Long customObjectId, @RequestParam Long customPropertyId) {
        customPropertyService.addProperty(customObjectId, customPropertyId);
        return Message.SUCCESS.toJSONObject().toJSONString();
    }

    @PostMapping(value = "/remove", params = {"customObjectId", "customPropertyId"})
    @ResponseBody
    public String removeProperty(@RequestParam Long customObjectId, @RequestParam Long customPropertyId) {
        customPropertyService.removeProperty(customObjectId, customPropertyId);
        return Message.SUCCESS.toJSONObject().toJSONString();
    }

    @PostMapping(value = "/save")
    @ResponseBody
    public String saveProperty(@RequestBody CustomProperty property) {
        if (property == null) {
            JSONObject jsonObject = Message.FAIL.toJSONObject();
            jsonObject.put("message", "Property not found");
            return jsonObject.toJSONString();
        }
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("property", customPropertyService.saveProperty(property));
        return jsonObject.toJSONString();
    }

    @PostMapping(value = "/update")
    @ResponseBody
    public String updateProperty(@RequestBody CustomProperty property) {
        customPropertyService.updateProperty(property);
        return Message.SUCCESS.toJSONObject().toJSONString();
    }

    @DeleteMapping(value = "/delete", params = "id")
    @ResponseBody
    public String deleteProperty(@RequestParam Long id) {
        customPropertyService.deleteProperty(id);
        return Message.SUCCESS.toJSONObject().toJSONString();
    }
}
