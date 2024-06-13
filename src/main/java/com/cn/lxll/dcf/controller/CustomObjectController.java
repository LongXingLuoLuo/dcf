package com.cn.lxll.dcf.controller;

import com.alibaba.fastjson2.JSONObject;
import com.cn.lxll.dcf.message.Message;
import com.cn.lxll.dcf.pojo.model.CustomObject;
import com.cn.lxll.dcf.service.CustomObjectService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Project dcf
 * Created on 2024/6/10 上午2:20
 *
 * @author Lxll
 */
@Log4j2
@RequestMapping(value = "/object", produces = "application/json;charset=utf-8")
@Controller
public class CustomObjectController {
    @Resource
    private CustomObjectService customObjectService;

    @GetMapping(value = "/get/all")
    @ResponseBody
    public String findAll() {
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("objects", customObjectService.findAllCustomObjects());
        return jsonObject.toJSONString();
    }

    @GetMapping(value = "/get", params = "id")
    @ResponseBody
    public String findById(Long id) {
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("object", customObjectService.findCustomObjectById(id));
        return jsonObject.toJSONString();
    }

    @GetMapping(value = "/get", params = "name")
    @ResponseBody
    public String findByName(String name) {
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("object", customObjectService.findCustomObjectByName(name));
        return jsonObject.toJSONString();
    }

    @GetMapping(value = "/get", params = "type")
    @ResponseBody
    public String findByType(String type) {
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("object", customObjectService.findCustomObjectByType(type));
        return jsonObject.toJSONString();
    }

    @PostMapping(value = "/save")
    @ResponseBody
    public String add(@RequestBody CustomObject customObject) {
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("object", customObjectService.saveCustomObject(customObject));
        return jsonObject.toJSONString();
    }

    @PostMapping(value = "/update")
    @ResponseBody
    public String update(@RequestBody CustomObject customObject) {
        customObjectService.updateCustomObject(customObject);
        return Message.SUCCESS.toJSONObject().toJSONString();
    }

    @DeleteMapping(value = "/delete")
    @ResponseBody
    public String delete(Long id) {
        customObjectService.deleteCustomObject(id);
        return Message.SUCCESS.toJSONObject().toJSONString();
    }
}
