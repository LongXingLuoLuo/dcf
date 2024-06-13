package com.cn.lxll.dcf.controller;

import com.alibaba.fastjson2.JSONObject;
import com.cn.lxll.dcf.message.Message;
import com.cn.lxll.dcf.pojo.model.Model;
import com.cn.lxll.dcf.service.ModelService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Project dcf
 * Created on 2024/6/11 下午9:45
 *
 * @author Lxll
 */
@Log4j2
@RequestMapping(value = "/model", produces = "application/json;charset=utf-8")
@Controller
public class ModelController {
    @Resource
    private ModelService modelService;

    /**
     * 获取所有模型
     * @return JSON 文本
     */
    @GetMapping(value = "/get/all")
    @ResponseBody
    public String getAllModels() {
        log.info("Get all models");
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("models", modelService.findAllModels());
        return jsonObject.toJSONString();
    }

    /**
     * 寻找所有包含指定信息的模型
     * @param infoId 信息id
     * @return JSON 文本
     */
    @GetMapping(value = "/get/all", params = "infoId")
    @ResponseBody
    public String getAllModelsByInfosContains(@RequestParam Long infoId) {
        log.info("Get all models by infoId: {}", infoId);
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("models", modelService.findAllByInfosContains(infoId));
        return jsonObject.toJSONString();
    }

    /**
     * 寻找所有包含指定信息的模型，并且名称包含指定字符串
     * @param infoId 信息id
     * @param name 模型名称
     * @return JSON 文本
     */
    @GetMapping(value = "/get/all", params = {"infoId", "name"})
    @ResponseBody
    public String getAllByInfosContainsAndNameLike(@RequestParam Long infoId, @RequestParam String name) {
        log.info("Get all models by infoId: {} and name: {}", infoId, name);
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("models", modelService.findAllByInfosContainsAndNameLike(infoId, name));
        return jsonObject.toJSONString();
    }

    /**
     * 通过id获取模型
     * @param id 模型id
     * @return JSON 文本
     */
    @GetMapping(value = "/get", params = "id")
    @ResponseBody
    public String getModelById(@RequestParam Long id) {
        log.info("Get model by id: {}", id);
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("model", modelService.findModelById(id));
        return jsonObject.toJSONString();
    }

    /**
     * 通过名称获取模型
     * @param name 模型名称
     * @return  JSON 文本
     */
    @GetMapping(value = "/get", params = "name")
    @ResponseBody
    public String getModelByName(@RequestParam String name) {
        log.info("Get model by name: {}", name);
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("model", modelService.findModelByName(name));
        return jsonObject.toJSONString();
    }

    /**
     * 保存模型
     * @param model 模型
     * @return JSON 文本
     */
    @PostMapping(value = "/save")
    @ResponseBody
    public String saveModel(@RequestBody Model model)  {
        log.info("Save model: {}", model);
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("model", modelService.saveModel(model));
        return jsonObject.toJSONString();
    }

    /**
     * 更新模型
     * @param model 模型
     * @return JSON 文本
     */
    @PostMapping(value = "/update")
    @ResponseBody
    public String updateModel(@RequestBody Model model) {
        log.info("Update model: {}", model);
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("model", modelService.updateModel(model));
        return jsonObject.toJSONString();
    }

    /**
     * 添加信息
     * @param modelId 模型id
     * @param infoId 信息id
     * @return JSON 文本
     */
    @PostMapping(value = "/update/infos/add")
    @ResponseBody
    public String addInfo(@RequestParam Long modelId, @RequestParam Long infoId) {
        log.info("Add info: {} by object: {}", modelId, infoId);
        modelService.addInfo(modelId, infoId);
        return Message.SUCCESS.toJSONObject().toJSONString();
    }

    /**
     * 移除信息
     * @param modelId 模型id
     * @param infoId 信息id
     * @return JSON 文本
     */
    @PostMapping(value = "/update/infos/remove")
    @ResponseBody
    public String removeInfo(@RequestParam Long modelId, @RequestParam Long infoId) {
        log.info("Remove info: {} by object: {}", modelId, infoId);
        modelService.removeInfo(modelId, infoId);
        return Message.SUCCESS.toJSONObject().toJSONString();
    }

    /**
     * 删除模型
     * @param id 模型id
     * @return JSON 文本
     */
    @DeleteMapping(value = "/delete", params = "id")
    @ResponseBody
    public String deleteModel(@RequestParam Long id) {
        log.info("Delete model by id: {}", id);
        modelService.deleteModel(id);
        return Message.SUCCESS.toJSONObject().toJSONString();
    }
}
