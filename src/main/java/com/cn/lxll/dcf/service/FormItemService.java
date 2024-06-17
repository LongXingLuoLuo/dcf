package com.cn.lxll.dcf.service;

import com.cn.lxll.dcf.dao.CustomObjectDao;
import com.cn.lxll.dcf.dao.CustomPropertyDao;
import com.cn.lxll.dcf.dao.FormDao;
import com.cn.lxll.dcf.dao.FormItemDao;
import com.cn.lxll.dcf.pojo.form.Form;
import com.cn.lxll.dcf.pojo.form.FormItem;
import com.cn.lxll.dcf.pojo.model.CustomObject;
import com.cn.lxll.dcf.pojo.model.CustomProperty;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Instant;
import java.util.ArrayList;
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
    private FormItemDao formItemDao;
    @Resource
    private FormDao formDao;
    @Resource
    private CustomPropertyDao customPropertyDao;
    @Resource
    private CustomObjectDao customObjectDao;

    public List<FormItem> findAll(){
        return formItemDao.findAll();
    }

    public List<FormItem> findAllByForm(Long fromId){
        List<FormItem> formItems = new ArrayList<>();
        formItemDao.findAllByFormId(fromId).forEach(formItem -> formItems.add(formItemDao.findById(formItem.getId()).orElse(null)));
        return formItems;
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
        CustomProperty ref = formItem.getRef();
        if (ref == null || ref.getId() == null){
            ref = null;
        } else {
            ref = customPropertyDao.findById(ref.getId()).orElse(null);
            if (ref == null){
                log.info("ref not found");
                return null;
            }
        }
        CustomObject object = formItem.getObject();
        if (object != null && object.getId() != null){
            object = customObjectDao.findById(object.getId()).orElse(null);
            if (object == null){
                log.info("object not found");
                return null;
            }
        }
        formItem.setId(null);
        formItem.setOrder(findMaxOrderOfForm(form.getId()) + 1);
        formItem = formItemDao.save(formItem);
        formItemDao.setFormById(formItem.getId(), form.getId());
        formDao.updateUpdateTimeById(form.getId(), Instant.now());
        if (ref != null) {
            formItemDao.setRef(formItem.getId(), ref.getId());
        }
        if (object != null){
            formItemDao.setObject(formItem.getId(), object.getId());
        }
        return formItemDao.findById(formItem.getId()).orElse(null);
    }

    public void update(FormItem formItem){
        formItem = formItemDao.save(formItem);
        if (formItem.getForm() != null && formItem.getForm().getId() != null){
            formDao.updateUpdateTimeById(formItem.getForm().getId(), Instant.now());
        }
    }

    public void updateRef(Long formItemId, Long refId) {
        formItemDao.removeRef(formItemId);
        formItemDao.setRef(formItemId, refId);
    }

    public void deleteById(Long id){
        FormItem formItem = formItemDao.findById(id).orElse(null);
        formItemDao.deleteById(id);
        if (formItem != null && formItem.getForm() != null && formItem.getForm().getId() != null){
            formDao.updateUpdateTimeById(formItem.getForm().getId(), Instant.now());
        }
    }
}
