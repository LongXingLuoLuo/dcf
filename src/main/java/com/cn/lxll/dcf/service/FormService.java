package com.cn.lxll.dcf.service;

import com.cn.lxll.dcf.dao.CustomObjectDao;
import com.cn.lxll.dcf.dao.FormDao;
import com.cn.lxll.dcf.dao.FormItemDao;
import com.cn.lxll.dcf.dao.UserDao;
import com.cn.lxll.dcf.pojo.User;
import com.cn.lxll.dcf.pojo.form.Form;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Project dcf
 *
 * @author Lxll
 */
@Log4j2
@Service
public class FormService {
    @Resource
    private FormDao formDao;
    @Resource
    private UserDao userDao;
    @Resource
    private FormItemDao formItemDao;
    @Resource
    private CustomObjectDao customObjectDao;

    public List<Form> findAllForms() {
        return formDao.findAll();
    }

    public Form findFormById(Long id) {
        return formDao.findById(id).orElse(null);
    }

    public Form save(Form form) {
        if (form == null) {
            return null;
        }
        User manager = form.getManager();
        if (manager == null || manager.getId() == null) {
            log.error("form manager is null");
            return null;
        }
        manager = userDao.findById(manager.getId()).orElse(null);
        if (manager == null) {
            log.error("manager not found");
            return null;
        }
        form.setCreateTime(Instant.now());
        form.setUpdateTime(Instant.now());
        form = formDao.save(form);
        formDao.setManager(form.getId(), manager.getId());
        return formDao.findById(form.getId()).orElse(null);
    }

    public void updateForm(Form form) {
        if (form == null || form.getId() == null) {
            return;
        }
        form.setUpdateTime(Instant.now());
        formDao.save(form);
    }

    public void updateManager(Long formId, Long managerId) {
        if (formId == null || managerId == null) {
            return;
        }
        if (!userDao.existsById(managerId)) {
            return;
        }
        formDao.removeManager(formId);
        formDao.setManager(formId, managerId);
    }

    public void updateRef(Long formId, Long refId) {
        formDao.removeRef(formId);
        formDao.setRef(formId, refId);
    }

    public void updateUpdateTime(Long id) {
        formDao.updateUpdateTimeById(id, new Date().toInstant());
    }

    public void updateVisit(Long userId, Long formId) {
        formDao.updateVisit(userId, formId, new Date());
    }

    public List<Form> getAllFormsByManager(Long managerId) {
        List<Form> forms = new ArrayList<>();
        formDao.findAllByManagerId(managerId).forEach(form -> forms.add(formDao.findById(form.getId()).orElse(null)));
        return forms;
    }

    public void deleteFormById(Long id) {
        formItemDao.deleteAllByFormId(id);
        formDao.deleteById(id);
    }
}
