package com.cn.lxll.dcf.service;

import com.cn.lxll.dcf.dao.FormDao;
import com.cn.lxll.dcf.dao.FormItemDao;
import com.cn.lxll.dcf.pojo.form.Form;
import com.cn.lxll.dcf.pojo.form.FormItem;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Project dcf
 *
 * @author Lxll
 */
@Log4j2
@Service
public class FormItemService {
    @Resource
    FormItemDao formItemDao;
    @Resource
    FormDao formDao;

    public List<FormItem> findAll(){
        return formItemDao.findAll();
    }

    public List<FormItem> findAllByForm(Long fromId){
        return formItemDao.findAllByFormId(fromId);
    }

    public FormItem getFormItemById(Long id){
        return formItemDao.findById(id).orElse(null);
    }

    public Integer findMaxOrderOfForm(Long formId){
        Integer maxOrder = formItemDao.maxOrderByFormId(formId);
        return maxOrder == null ? 0 : maxOrder;
    }

    public FormItem save(FormItem formItem){
        if (formItem == null){
            log.error("formItem is null.");
            return null;
        }
        if (formItem.getForm() == null || formItem.getForm().getId() == null){
            log.error("form is null.");
            return null;
        }
        Form form = formDao.findById(formItem.getForm().getId()).orElse(null);
        if(form == null){
            log.error("form not found");
            return null;
        }
        formItem.setId(null);
        formItem.setOrder(findMaxOrderOfForm(form.getId()) + 1);
        formItem = formItemDao.save(formItem);
        formItemDao.setFormById(formItem.getId(), form.getId());
        formDao.updateUpdateTimeById(form.getId(), new Date());
        return formItemDao.findById(formItem.getId()).orElse(null);
    }

    public void update(FormItem formItem){
        formItem = formItemDao.save(formItem);
        if (formItem.getForm() != null && formItem.getForm().getId() != null){
            formDao.updateUpdateTimeById(formItem.getForm().getId(), new Date());
        }
    }

    public void deleteById(Long id){
        FormItem formItem = formItemDao.findById(id).orElse(null);
        formItemDao.deleteById(id);
        if (formItem != null && formItem.getForm() != null && formItem.getForm().getId() != null){
            formDao.updateUpdateTimeById(formItem.getForm().getId(), new Date());
        }
    }
}
