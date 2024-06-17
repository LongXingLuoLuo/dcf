package com.cn.lxll.dcf.service;

import com.cn.lxll.dcf.dao.CustomObjectDao;
import com.cn.lxll.dcf.dao.CustomPropertyDao;
import com.cn.lxll.dcf.pojo.model.CustomObject;
import com.cn.lxll.dcf.pojo.model.CustomProperty;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Project dcf
 * Created on 2024/6/10 上午1:47
 *
 * @author Lxll
 */
@Log4j2
@Service
public class CustomPropertyService {
    @Resource
    private CustomPropertyDao customPropertyDao;
    @Resource
    private CustomObjectDao customObjectDao;

    public List<CustomProperty> findAllProperties() {
        return customPropertyDao.findAll();
    }

    public List<CustomProperty> findAllPropertiesByCustomObject(Long customObjectId) {
        List<CustomProperty> oldCustomProperties = customPropertyDao.findAllByCustomObject(customObjectId);
        List<CustomProperty> newCustomProperties = new ArrayList<>();
        for (CustomProperty oldCustomProperty : oldCustomProperties) {
            newCustomProperties.add(customPropertyDao.findById(oldCustomProperty.getId()).orElse(null));
        }
        return newCustomProperties;
    }

    public List<CustomProperty> findAllPropertiesByModel(Long modelId) {
        List<CustomProperty> oldCustomProperties = customPropertyDao.findAllByModel(modelId);
        List<CustomProperty> newCustomProperties = new ArrayList<>();
        for (CustomProperty oldCustomProperty : oldCustomProperties) {
            log.info("findAllPropertiesByModel: oldCustomProperty={}", oldCustomProperty);
            newCustomProperties.add(customPropertyDao.findById(oldCustomProperty.getId()).orElse(null));
        }
        return newCustomProperties;
    }

    public CustomProperty findPropertyById(Long id) {
        return customPropertyDao.findById(id).orElse(null);
    }

    public CustomProperty findPropertyByCustomObjectAndKey(Long customObjectId, String key) {
        CustomProperty customProperty = customPropertyDao.findByCustomObjectAndKey(customObjectId, key).orElse(null);
        if (customProperty != null) {
            return customPropertyDao.findById(customProperty.getId()).orElse(null);
        }
        return null;
    }

    public void addProperty(Long customObjectId, Long customPropertyId) {
        customPropertyDao.addProperty(customObjectId, customPropertyId);
    }

    public void removeProperty(Long customObjectId, Long customPropertyId) {
        customPropertyDao.removeProperty(customObjectId, customPropertyId);
    }

    public CustomProperty saveProperty(CustomProperty customProperty) {
        if (customProperty == null){
            return null;
        }
        CustomObject ref = null;
        if (customProperty.getRef() != null && customProperty.getRef().getId() != null) {
            ref = customObjectDao.findById(customProperty.getRef().getId()).orElse(null);
        }
        customProperty.setId(null);
        customProperty =  customPropertyDao.save(customProperty);
        if (ref != null){
            customPropertyDao.setRef(customProperty.getId(), ref.getId());
        }
        return customPropertyDao.findById(customProperty.getId()).orElse(null);
    }

    public void updateProperty(CustomProperty customProperty) {
        if (customProperty == null){
            return;
        }
        CustomObject ref = null;
        if (customProperty.getRef() != null && customProperty.getRef().getId() != null) {
            ref = customObjectDao.findById(customProperty.getRef().getId()).orElse(null);
        }
        customProperty =  customPropertyDao.save(customProperty);
        customPropertyDao.removeRef(customProperty.getId());
        if (ref != null){
            customPropertyDao.setRef(customProperty.getId(), ref.getId());
        }
    }

    public void deleteProperty(Long id) {
        customPropertyDao.deleteById(id);
    }
}
