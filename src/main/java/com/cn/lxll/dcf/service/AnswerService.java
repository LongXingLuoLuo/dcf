package com.cn.lxll.dcf.service;

import com.alibaba.fastjson2.JSONArray;
import com.cn.lxll.dcf.dao.AnswerRecordDao;
import com.cn.lxll.dcf.dao.AnsweredDao;
import com.cn.lxll.dcf.pojo.form.AnswerRecord;
import com.cn.lxll.dcf.pojo.form.Answered;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    private AnsweredDao answeredDao;
    @Resource
    private AnswerRecordDao answerRecordDao;

    public Integer saveContentByUserIdAndFormItemId(Long userId, Long formItemId, String content) {
        return answeredDao.saveContentByUserIdAndFormItemId(userId, formItemId, content);
    }

    public String getContentByUserIdAndFormItemId(Long userId, Long formItemId) {
        return answeredDao.findContentByUserIdAndFormItemId(userId, formItemId).orElse(null);
    }

    public Answered getAnsweredByUserIdAndFormItemId(Long userId, Long formItemId) {
        return answeredDao.findByUserIdAndFormItemId(userId, formItemId).orElse(null);
    }

    public void deleteAnsweredByUserIdAndFormItemId(Long userId, Long formItemId) {
        answeredDao.deleteByUserIdAndFormItemId(userId, formItemId);
    }

    public void deleteAnsweredByAnsweredId(Long id) {
        answeredDao.deleteById(id);
    }

    public Answered getAnsweredByAnsweredId(Long id) {
        return answeredDao.findByAnsweredId(id).orElse(null);
    }

    public JSONArray getAllContentsByFormItemId(Long formItemId) {
        List<AnswerRecord> answerRecordList = answerRecordDao.findAllContentByFormItemId(formItemId);
        JSONArray jsonArray = new JSONArray();
        for (AnswerRecord answerRecord : answerRecordList) {
            jsonArray.add(answerRecord.getContent());
        }
        return jsonArray;
    }

    public List<AnswerRecord> getAllContentsByFormId(Long formId) {
        return answerRecordDao.findByFormId(formId);
    }
}
