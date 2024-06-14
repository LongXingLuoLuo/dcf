package com.cn.lxll.dcf.controller;

import com.alibaba.fastjson2.JSONObject;
import com.cn.lxll.dcf.message.Message;
import com.cn.lxll.dcf.pojo.form.Answer;
import com.cn.lxll.dcf.service.AnswerService;
import com.cn.lxll.dcf.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 答案接口
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

    /**
     * 获取所有答案
     * @return 所有答案
     */
    @ResponseBody
    @GetMapping(value = "/get/all")
    public String getAllAnswers() {
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("answers", answerService.findAll());
        return jsonObject.toJSONString();
    }

    /**
     * 获取用户对某个表单项的所有答案
     * @param userId 用户ID
     * @param formItemId 表单项ID
     * @return 用户对某个表单项的所有答案
     */
    @ResponseBody
    @GetMapping(value = "/get/all", params = {"userId", "formItemId"})
    public String getAllAnswersByUserIdAndFormItemId(Long userId, Long formItemId) {
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("answers", answerService.findAllByUserIdAndFormItemId(userId, formItemId));
        return jsonObject.toJSONString();
    }

    /**
     * 获取某个表单项的所有答案
     * @param formItemId 表单项ID
     * @return 某个表单项的所有答案
     */
    @ResponseBody
    @GetMapping(value = "/get/all", params = {"formItemId"})
    public String getAllAnswersByFormItemId(Long formItemId) {
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("answers", answerService.findAllByFormItem(formItemId));
        return jsonObject.toJSONString();
    }

    /**
     * 获取某个表单的所有答案
     * @param formId 表单ID
     * @return 某个表单的所有答案
     */
    @ResponseBody
    @GetMapping(value = "/get/all", params = {"formId"})
    public String getAllAnswersByFormId(Long formId) {
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("answers", answerService.findAllByForm(formId));
        return jsonObject.toJSONString();
    }

    /**
     * 获取答案
     * @param id 答案ID
     * @return 答案
     */
    @ResponseBody
    @GetMapping(value = "/get", params = "id")
    public String getAnswerById(@RequestParam Long id) {
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        jsonObject.put("answer", answerService.findById(id));
        return jsonObject.toJSONString();
    }

    /**
     * 保存答案
     * @param answer 答案
     * @return 保存结果
     */
    @ResponseBody
    @PostMapping(value = "/save")
    public String saveAnswer(@RequestBody Answer answer) {
        log.info("Save Answer: {}", answer);
        answer = answerService.save(answer);
        if (answer == null) {
            return Message.FAIL.toJSONObject().toJSONString();
        } else {
            JSONObject jsonObject = Message.SUCCESS.toJSONObject();
            jsonObject.put("answer", answer);
            return jsonObject.toJSONString();
        }
    }

    /**
     * 更新答案
     * @param answer 答案
     * @return 更新结果
     */
    @ResponseBody
    @PostMapping(value = "/update")
    public String update(@RequestBody Answer answer) {
        log.info("Update Answer: {}", answer);
        answerService.update(answer);
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        return jsonObject.toJSONString();
    }

    /**
     * 更新答案关联的模型
     * @param answerId 答案ID
     * @param modelId 模型ID
     * @return 更新结果
     */
    @ResponseBody
    @PostMapping(value = "/update/ref", params = {"answerId", "modelId"})
    public String updateRef(@RequestParam Long answerId, @RequestParam Long modelId) {
        log.info("Update Answer Ref: answerId={}, modelId={}", answerId, modelId);
        answerService.updateRef(answerId, modelId);
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        return jsonObject.toJSONString();
    }

    /**
     * 更新答案关联的表单项
     * @param answerId 答案ID
     * @param formItemId 表单项ID
     * @return 更新结果
     */
    @ResponseBody
    @PostMapping(value = "/update/formItem", params = {"answerId", "formItemId"})
    public String updateFormItem(@RequestParam Long answerId, @RequestParam Long formItemId) {
        log.info("Update Answer FormItem: answerId={}, formItemId={}", answerId, formItemId);
        answerService.updateFormItem(answerId, formItemId);
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        return jsonObject.toJSONString();
    }

    /**
     * 更新答案关联的用户
     * @param answerId 答案ID
     * @param userId 用户ID
     * @return 更新结果
     */
    @ResponseBody
    @PostMapping(value = "/update/user", params = {"answerId", "userId"})
    public String updateUser(@RequestParam Long answerId, @RequestParam Long userId) {
        log.info("Update Answer User: answerId={}, userId={}", answerId, userId);
        answerService.updateUser(answerId, userId);
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        return jsonObject.toJSONString();
    }

    /**
     * 删除答案
     * @param id 答案ID
     * @return 删除结果
     */
    @ResponseBody
    @DeleteMapping(value = "/delete", params = "id")
    public String deleteAnswerById(@RequestParam Long id) {
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        answerService.deleteById(id);
        return jsonObject.toJSONString();
    }

    /**
     * 删除用户对某个表单项的所有答案
     * @param userId 用户ID
     * @param formItemId 表单项ID
     * @return 删除结果
     */
    @ResponseBody
    @DeleteMapping(value = "/delete", params = {"userId", "formItemId"})
    public String deleteAnswerByUserAndFormItem(@RequestParam Long userId, @RequestParam Long formItemId) {
        JSONObject jsonObject = Message.SUCCESS.toJSONObject();
        answerService.deleteByUserAndFormItem(userId, formItemId);
        return jsonObject.toJSONString();
    }
}
