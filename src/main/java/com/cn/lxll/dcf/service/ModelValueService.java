package com.cn.lxll.dcf.service;

import com.cn.lxll.dcf.dao.CustomPropertyDao;
import com.cn.lxll.dcf.dao.ModelDao;
import com.cn.lxll.dcf.dao.ModelValueDao;
import com.cn.lxll.dcf.pojo.model.CustomProperty;
import com.cn.lxll.dcf.pojo.model.Model;
import com.cn.lxll.dcf.pojo.model.ModelValue;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Project dcf
 * Created on 2024/6/11 下午11:30
 *
 * @author Lxll
 */
@Log4j2
@Service
public class ModelValueService {
    @Resource
    private ModelValueDao modelValueDao;
    @Resource
    private CustomPropertyDao customPropertyDao;
    @Resource
    private ModelDao modelDao;

    public List<ModelValue> findAllByModelANDProperty(Long modelId, Long propertyId) {
        List<ModelValue> modelValues = new ArrayList<>();
        modelValueDao.findAllByModelANDProperty(modelId, propertyId).forEach(modelValue -> {
            modelValues.add(modelValueDao.findById(modelValue.getId()).orElse(null));
        });
        return modelValues;
    }

    public List<ModelValue> findAllByModel(Long modelId) {
        return modelValueDao.findAllByModel(modelId);
    }

    public List<ModelValue> findAll() {
        return modelValueDao.findAll();
    }

    public ModelValue findById(Long id) {
        return modelValueDao.findById(id).orElse(null);
    }

    public ModelValue save(ModelValue modelValue) {
        if (modelValue == null)
            return null;
        modelValue.setId(null);
        if (modelValue.getInfo() == null) {
            return null;
        }
        CustomProperty info = customPropertyDao.findById(modelValue.getInfo().getId()).orElse(null);
        if (info == null) {
            return null;
        }
        if (modelValue.getOwner() == null) {
            return null;
        }
        Model owner = modelDao.findById(modelValue.getOwner().getId()).orElse(null);
        if (owner == null) {
            return null;
        }
        Model ref = null;
        if (modelValue.getRef() != null) {
            ref = modelDao.findById(modelValue.getRef().getId()).orElse(null);
        }
        modelValue = modelValueDao.save(modelValue);
        modelValueDao.setInfo(modelValue.getId(), info.getId());
        modelValueDao.setOwner(modelValue.getId(), owner.getId());
        if (ref != null) {
            log.info("Set ref: {} to value: {}", ref.getId(), modelValue.getId());
            modelValueDao.setRef(modelValue.getId(), ref.getId());
        }
        return modelValueDao.findById(modelValue.getId()).orElse(null);
    }

    public void update(ModelValue modelValue) {
        modelValueDao.save(modelValue);
    }

    public void setInfo(Long valueId, Long infoId) {
        modelValueDao.removeInfo(valueId);
        modelValueDao.setInfo(valueId, infoId);
    }

    public void setRef(Long valueId, Long refId) {
        modelValueDao.removeRef(valueId);
        modelValueDao.setRef(valueId, refId);
    }

    public void delete(Long id) {
        modelValueDao.deleteById(id);
    }
}
