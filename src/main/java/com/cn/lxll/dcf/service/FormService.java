package com.cn.lxll.dcf.service;

import com.cn.lxll.dcf.dao.FormDao;
import com.cn.lxll.dcf.dao.UserDao;
import com.cn.lxll.dcf.pojo.User;
import com.cn.lxll.dcf.pojo.form.Form;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
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
    FormDao formDao;

    @Resource
    UserDao userDao;

    public void save(Form form) {
        formDao.save(form);
    }

    public Form addForm(Form form) {
        if (form.getManager() == null || form.getManager().getId() == null) {
            log.error("form manager is null");
            return null;
        }
        User manager = userDao.findById(form.getManager().getId()).orElse(null);
        if (manager == null) {
            log.error("manager not found");
            return null;
        }
        form.setManager(manager);
        form.setId(null);
        form.setCreateTime(new Date());
        form.setUpdateTime(new Date());
        if (form.getTitle() == null) {
            form.setTitle("Untitled Form");
        }
        return formDao.save(form);
    }

    public void updateFormWithoutManager(Form form) {
        if (form == null || form.getId() == null) {
            return;
        }
        Form oldForm = formDao.findById(form.getId()).orElse(null);
        if (oldForm != null) {
            form.setManager(oldForm.getManager());
            form.setCreateTime(oldForm.getCreateTime());
            form.setUpdateTime(new Date());
            formDao.save(form);
        }
    }

    public void updateTitle(Form form) {
        if (form == null || form.getId() == null || form.getTitle() == null) {
            return;
        }
        form.setUpdateTime(new Date());
        formDao.updateTitleById(form.getId(), form.getTitle());
    }

    public void updateDescription(Form form) {
        if (form == null || form.getId() == null) {
            return;
        }
        form.setUpdateTime(new Date());
        formDao.updateDescriptionById(form.getId(), form.getDescription());
    }

    public void updateManager(Form form) {
        if (form == null || form.getId() == null || form.getManager() == null || form.getManager().getId() == null) {
            return;
        }
        if (!userDao.existsById(form.getManager().getId())) {
            return;
        }
        formDao.updateManagerById(form.getId(), form.getManager().getId());
    }

    public void updateUpdateTime(Form form) {
        if (form == null || form.getId() == null) {
            return;
        }
        form.setUpdateTime(new Date());
        formDao.updateUpdateTimeById(form.getId(), form.getUpdateTime());
    }

    public boolean isManager(User user, Form form) {
        if (user == null || user.getId() == null || form == null || form.getId() == null) {
            log.error("user or form is null");
            return false;
        }
        return formDao.isManager(form.getId(), user.getId());
    }

    public List<Form> getAllFormsByManager(User manager) {
        if (manager == null || manager.getId() == null) {
            return Collections.emptyList();
        }
        return formDao.findAllByManagerId(manager.getId());
    }

    public Form getFormById(Long id) {
        return formDao.findById(id).orElse(null);
    }

    public List<Form> getAllForms() {
        return formDao.findAll();
    }

    public boolean isFormExist(Long id) {
        return formDao.existsById(id);
    }

    public void deleteFormById(Long id) {
        formDao.deleteById(id);
    }
}
