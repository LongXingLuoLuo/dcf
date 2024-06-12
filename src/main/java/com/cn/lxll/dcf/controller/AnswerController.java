package com.cn.lxll.dcf.controller;

import com.alibaba.fastjson2.JSONObject;
import com.cn.lxll.dcf.message.Message;
import com.cn.lxll.dcf.service.AnswerService;
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
@RequestMapping(value = "/form/answer", produces = "application/json;charset=utf-8")
@Controller
public class AnswerController {
    @Resource
    private AnswerService answerService;
    @Resource
    private UserService userService;

    @ResponseBody
    @GetMapping("/get/{id:\\d+}")
    public String getAnswerById(@PathVariable Long id) {
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("answer", answerService.getAnsweredByAnsweredId(id));
        return jsonObject.toJSONString();
    }

    @ResponseBody
    @GetMapping(value = "/get", params = {"userId", "formItemId"})
    public String getAnsweredByUserIdAndFormItemId(Long userId, Long formItemId) {
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("answered", answerService.getAnsweredByUserIdAndFormItemId(userId, formItemId));
        return jsonObject.toJSONString();
    }

    @ResponseBody
    @GetMapping(value = "/get/content", params = {"userId", "formItemId"})
    public String getContentByUserIdAndFormItemId(Long userId, Long formItemId) {
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("content", answerService.getContentByUserIdAndFormItemId(userId, formItemId));
        return jsonObject.toJSONString();
    }

    @ResponseBody
    @GetMapping(value = "/get/all", params = {"formItemId"})
    public String getAllAnswersByFormItemId(Long formItemId) {
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("answers", answerService.getAllContentsByFormItemId(formItemId));
        return jsonObject.toJSONString();
    }

    @ResponseBody
    @GetMapping(value = "/get/all", params = {"formId"})
    public String getAllAnswersByFormId(Long formId) {
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("answers", answerService.getAllContentsByFormId(formId));
        return jsonObject.toJSONString();
    }

    @ResponseBody
    @PostMapping(value = "/update/content", params = {"userId", "formItemId"})
    public String updateContentByUserIdAndFormItemId(@RequestParam Long userId, @RequestParam Long formItemId, @RequestParam(required = false) String content) {
//        if (content == null) {
//            JSONObject jsonObject = Message.FAIL.toJSONObject();
//            jsonObject.put("message", "content 为 null");
//            return jsonObject.toJSONString();
//        }
        log.info("userId: {}, formItemId: {}, content: {}", userId, formItemId, content);
        answerService.saveContentByUserIdAndFormItemId(userId, formItemId, content);
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        return jsonObject.toJSONString();
    }

    @ResponseBody
    @DeleteMapping(value = "/delete/{id:\\d+}")
    public String deleteAnswer(@PathVariable Long id) {
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        answerService.deleteAnsweredByAnsweredId(id);
        return jsonObject.toJSONString();
    }

    @ResponseBody
    @DeleteMapping(value = "/delete", params = {"userId", "formItemId"})
    public String deleteAnswer(@RequestParam Long userId, @RequestParam Long formItemId) {
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        answerService.deleteAnsweredByUserIdAndFormItemId(userId, formItemId);
        return jsonObject.toJSONString();
    }
}
