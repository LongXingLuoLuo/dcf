package com.cn.lxll.dcf.service;

import com.cn.lxll.dcf.dao.AnswerDao;
import com.cn.lxll.dcf.dao.FormItemDao;
import com.cn.lxll.dcf.dao.ModelDao;
import com.cn.lxll.dcf.dao.UserDao;
import com.cn.lxll.dcf.pojo.User;
import com.cn.lxll.dcf.pojo.form.Answer;
import com.cn.lxll.dcf.pojo.form.FormItem;
import com.cn.lxll.dcf.pojo.model.Model;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Project dcf
 *
 * @author Lxll
 */
@Log4j2
@Service
public class AnswerService {
    @Resource
    private AnswerDao answerDao;
    @Resource
    private UserDao userDao;
    @Resource
    private FormItemDao formItemDao;
    @Resource
    private ModelDao modelDao;

    public List<Answer> findAll(){
        return answerDao.findAll();
    }

    public List<Answer> findAllByUserIdAndFormItemId(Long userId, Long formItemId) {
        List<Answer> answers = new ArrayList<>();
        answerDao.findAllByUserAndFormItem(userId, formItemId).forEach(answer -> answers.add(answerDao.findById(answer.getId()).orElse(null)));
        return answers;
    }

    public List<Answer> findAllByForm(Long formId) {
        List<Answer> answers = new ArrayList<>();
        answerDao.findAllByForm(formId).forEach(answer -> answers.add(answerDao.findById(answer.getId()).orElse(null)));
        return answers;
    }

    public List<Answer> findAllByFormItem(Long formItemId) {
        List<Answer> answers = new ArrayList<>();
        answerDao.findAllByFormItem(formItemId).forEach(answer -> answers.add(answerDao.findById(answer.getId()).orElse(null)));
        return answers;
    }

    public Answer findById(Long id) {
        return answerDao.findById(id).orElse(null);
    }

    public Answer save(Answer answer) {
        if (answer == null) {
            log.error("answer is null.");
            return null;
        }
        User user = answer.getUser();
        if (user == null || user.getId() == null) {
            log.error("user is null.");
            return null;
        }
        user = userDao.findById(user.getId()).orElse(null);
        if (user == null) {
            log.error("user not found.");
            return null;
        }
        FormItem formItem = answer.getFormItem();
        if (formItem == null || formItem.getId() == null) {
            log.error("formItem is null.");
            return null;
        }
        formItem = formItemDao.findById(formItem.getId()).orElse(null);
        if (formItem == null) {
            log.error("formItem not found.");
            return null;
        }
        Model ref = answer.getRef();
        if (ref != null && ref.getId() != null){
            ref = modelDao.findById(ref.getId()).orElse(null);
            if (ref == null) {
                log.error("ref not found.");
                return null;
            }
        } else {
            ref = null;
        }
        answer = answerDao.save(answer);
        updateUser(answer.getId(), user.getId());
        updateFormItem(answer.getId(), formItem.getId());
        if (ref != null) {
            answerDao.setRef(answer.getId(), ref.getId());
        }
        return answerDao.findById(answer.getId()).orElse(null);
    }

    public void update(Answer answer) {
        Model ref = answer.getRef();
        if (ref != null){
            ref = modelDao.findById(ref.getId()).orElse(null);
            if (ref == null) {
                log.error("ref not found.");
                return;
            }
        }
        answer = answerDao.save(answer);
        if (ref != null) {
            answerDao.setRef(answer.getId(), ref.getId());
        }
    }

    public void updateFormItem(Long answerId, Long formItemId) {
        if (formItemId == null || !formItemDao.findById(formItemId).isPresent()) {
            log.error("not found.");
            return;
        }
        answerDao.removeFormItem(answerId);
        answerDao.setFormItem(answerId, formItemId);
    }

    public void updateUser(Long answerId, Long userId) {
        if (userId == null || !userDao.findById(userId).isPresent()) {
            log.error("userId is null.");
            return;
        }
        answerDao.removeUser(answerId);
        answerDao.setUser(answerId, userId);
    }

    public void updateRef(Long answerId, Long modelId) {
        if (modelId == null || !modelDao.findById(modelId).isPresent()) {
            log.error("modelId is null.");
            return;
        }
        answerDao.removeRef(answerId);
        answerDao.setRef(answerId, modelId);
    }

    public void deleteByUserAndFormItem(Long userId, Long formItemId) {
        answerDao.deleteByUserAndFormItem(userId, formItemId);
    }

    public void deleteById(Long id) {
        answerDao.deleteById(id);
    }
}
