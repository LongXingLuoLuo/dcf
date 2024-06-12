package com.cn.lxll.dcf.service;

import com.cn.lxll.dcf.dao.CustomObjectDao;
import com.cn.lxll.dcf.dao.CustomPropertyDao;
import com.cn.lxll.dcf.pojo.model.CustomObject;
import com.cn.lxll.dcf.pojo.model.CustomProperty;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
        if (customProperty != null){
            customProperty.setId(null);
        }
        if (customProperty != null) {
            return customPropertyDao.save(customProperty);
        }
        return null;
    }

    public CustomProperty saveBasicProperty(String key, String type, boolean arr) {
        log.info("saveBasicProperty: key={}, type={}, arr={}", key, type, arr);
        CustomProperty customProperty = new CustomProperty();
        customProperty.setKey(key);
        customProperty.setType(type);
        customProperty.setArr(arr);
        return customPropertyDao.save(customProperty);
    }

    public CustomProperty saveRefProperty(String key, boolean arr, Long refId) {
        log.info("saveRefProperty: key={}, arr={}, refId={}", key, arr, refId);
        CustomObject ref = customObjectDao.findById(refId).orElse(null);
        if (ref == null) {
            return null;
        }
        CustomProperty customProperty = new CustomProperty();
        customProperty.setKey(key);
        customProperty.setType("Object");
        customProperty.setArr(arr);
        customProperty = customPropertyDao.save(customProperty);
        customPropertyDao.setRef(customProperty.getId(), refId);
        return customPropertyDao.findById(customProperty.getId()).orElse(null);
    }

    public void updateBasicProperty(Long id, String key, String type, boolean arr) {
        CustomProperty oldCustomProperty = customPropertyDao.findById(id).orElse(null);
        if (oldCustomProperty == null) {
            return;
        }
        oldCustomProperty.setKey(key);
        oldCustomProperty.setType(type);
        oldCustomProperty.setArr(arr);
        customPropertyDao.removeRef(id);
        customPropertyDao.save(oldCustomProperty);
    }

    public void updateRefProperty(Long id, String key, boolean arr, Long refId) {
        CustomProperty oldCustomProperty = customPropertyDao.findById(id).orElse(null);
        if (oldCustomProperty == null) {
            return;
        }
        oldCustomProperty.setKey(key);
        oldCustomProperty.setType("Object");
        oldCustomProperty.setArr(arr);
        customPropertyDao.removeRef(id);
        customPropertyDao.setRef(id, refId);
        customPropertyDao.save(oldCustomProperty);
    }

    public void updateProperty(CustomProperty customProperty) {
        customPropertyDao.save(customProperty);
    }

    public void deleteProperty(Long id) {
        customPropertyDao.deleteById(id);
    }
}
