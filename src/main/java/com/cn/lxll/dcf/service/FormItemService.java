package com.cn.lxll.dcf.service;

import com.cn.lxll.dcf.dao.FormDao;
import com.cn.lxll.dcf.dao.FormItemDao;
import com.cn.lxll.dcf.dao.HasItemDao;
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
    @Resource
    HasItemDao hasItemDao;

    public FormItem addFormItem(FormItem formItem, Form form){
        if (formItem == null){
            log.error("formItem is null.");
            return null;
        }
        if (form == null || form.getId() == null){
            log.error("form is null.");
            return null;
        }
        form = formDao.findById(form.getId()).orElse(null);
        if(form == null){
            log.error("form not found: {}", form);
            return null;
        }
        formDao.updateUpdateTimeById(form.getId(), new Date());
        formItem.setId(null);
        formItem.setHasItem(null);
        formItem = formItemDao.save(formItem);
        formItemDao.updateFormById(formItem.getId(), form.getId());
        return formItem;
    }

    public Integer maxOrder(Form form){
        if (form == null || form.getId() == null){
            log.error("form is null.");
            return 0;
        }
        Integer maxOrder = hasItemDao.maxOrderByFormId(form.getId());
        return maxOrder == null ? 0 : maxOrder;
    }

    public void updateFormItemWithoutForm(FormItem formItem){
        if (formItem == null) return;
        FormItem oldFormItem = formItemDao.findById(formItem.getId()).orElse(null);
        if (oldFormItem != null){
            formItem.setHasItem(oldFormItem.getHasItem());
            formItemDao.save(formItem);
            formDao.updateUpdateTimeByFormItemId(formItem.getId(), new Date());
        }
    }

    public void updateLabel(FormItem formItem){
        if (formItem == null){
            return;
        }
        formItemDao.updateLabelById(formItem.getId(), formItem.getLabel());
        formDao.updateUpdateTimeByFormItemId(formItem.getId(), new Date());

    }

    public void updateOptions(FormItem formItem){
        if (formItem == null){
            return;
        }
        formItemDao.updateOptionsById(formItem.getId(), formItem.getOptions());
        formDao.updateUpdateTimeByFormItemId(formItem.getId(), new Date());

    }

    public FormItem save(FormItem formItem){
        formDao.updateUpdateTimeByFormItemId(formItem.getId(), new Date());
        return formItemDao.save(formItem);

    }

    public FormItem getFormItemById(Long id){
        return formItemDao.findById(id).orElse(null);
    }

    public List<FormItem> getAllFormItemsByForm(Form form){
        if (form != null && form.getId() != null){
            return formItemDao.findAllByFormId(form.getId());
        }
        return null;
    }

    public List<FormItem> getAllFormItems(){
        return formItemDao.findAll();
    }

    public void deleteFormItemById(Long id){
        formItemDao.deleteById(id);
    }
}
