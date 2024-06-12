package com.cn.lxll.dcf.controller;

import com.alibaba.fastjson2.JSONObject;
import com.cn.lxll.dcf.message.Message;
import com.cn.lxll.dcf.pojo.model.ModelValue;
import com.cn.lxll.dcf.service.ModelValueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Project dcf
 * Created on 2024/6/12 上午12:30
 *
 * @author Lxll
 */
@Slf4j
@RequestMapping(value = "/model/value", produces = "application/json;charset=utf-8")
@Controller
public class ModelValueController {
    @Resource
    private ModelValueService modelValueService;

    /**
     * 获取某个模型的所有值
     * @param modelId 模型id
     * @return JSON字符串
     */
    @GetMapping(value = "/get/all", params = "modelId")
    @ResponseBody
    public String getAllByModel(@RequestParam Long modelId) {
        log.info("Get all modelValues by modelId: {}", modelId);
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("values", modelValueService.findAllByModel(modelId));
        return jsonObject.toJSONString();
    }

    /**
     * 获取某个模型的某个属性的所有值
     * @param modelId 模型id
     * @param propertyId 属性id
     * @return JSON字符串
     */
    @GetMapping(value = "/get/all", params = {"modelId", "propertyId"})
    @ResponseBody
    public String getAllByModelANDProperty(@RequestParam Long modelId, @RequestParam Long propertyId) {
        log.info("Get all modelValues by modelId: {} and propertyId: {}", modelId, propertyId);
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("values", modelValueService.findAllByModelANDProperty(modelId, propertyId));
        return jsonObject.toJSONString();
    }

    /**
     * 获取所有值
     * @return JSON字符串
     */
    @GetMapping(value = "/get/all")
    @ResponseBody
    public String getAll() {
        log.info("Get all modelValues");
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("values", modelValueService.findAll());
        return jsonObject.toJSONString();
    }

    /**
     * 获取值
     * @param id 值id
     * @return JSON字符串
     */
    @GetMapping(value = "/get", params = "id")
    @ResponseBody
    public String get(@RequestParam Long id) {
        log.info("Get modelValue: {}", id);
        ModelValue modelValue = modelValueService.findById(id);
        if (modelValue == null)
            return Message.FAIL.toJSONObject().toJSONString();
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("value", modelValue);
        return jsonObject.toJSONString();
    }

    /**
     * 新建值
     * @param modelValue 值s
     * @return JSON字符串
     */
    @PostMapping(value = "/save")
    @ResponseBody
    public String save(@RequestBody ModelValue modelValue) {
        log.info("Save modelValue: {}", modelValue);
        modelValue = modelValueService.save(modelValue);
        if (modelValue == null)
            return Message.FAIL.toJSONObject().toJSONString();
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("value", modelValue);
        return jsonObject.toJSONString();
    }

    /**
     * 更新值
     * @param modelValue 值
     * @return JSON字符串
     */
    @PostMapping(value = "/update")
    @ResponseBody
    public String update(@RequestBody ModelValue modelValue) {
        log.info("Update modelValue: {}", modelValue);
        modelValueService.update(modelValue);
        return Message.SUCCESS.toJSONObject().toJSONString();
    }

    /**
     * 更新值的引用
     * @param valueId 值id
     * @param refId 指向id
     * @return JSON字符串
     */
    @PostMapping(value = "/update/ref")
    @ResponseBody
    public String updateRef(@RequestParam Long valueId, @RequestParam Long refId) {
        log.info("Update modelValue ref: valueId: {}, refId: {}", valueId, refId);
        modelValueService.setRef(valueId, refId);
        return Message.SUCCESS.toJSONObject().toJSONString();
    }

    /**
     * 更新值的信息
     * @param valueId 值id
     * @param infoId 信息id
     * @return JSON字符串
     */
    @PostMapping(value = "/update/info")
    @ResponseBody
    public String updateInfo(@RequestParam Long valueId, @RequestParam Long infoId) {
        log.info("Update modelValue info: valueId: {}, infoId: {}", valueId, infoId);
        modelValueService.setInfo(valueId, infoId);
        return Message.SUCCESS.toJSONObject().toJSONString();
    }

    /**
     * 删除值
     * @param id 值id
     * @return JSON字符串
     */
    @DeleteMapping(value = "/delete")
    @ResponseBody
    public String delete(@RequestParam Long id) {
        log.info("Delete modelValue: {}", id);
        modelValueService.delete(id);
        return Message.SUCCESS.toJSONObject().toJSONString();
    }
}
