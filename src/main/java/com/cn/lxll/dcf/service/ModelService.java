package com.cn.lxll.dcf.service;

import com.cn.lxll.dcf.dao.CustomObjectDao;
import com.cn.lxll.dcf.dao.ModelDao;
import com.cn.lxll.dcf.pojo.model.CustomObject;
import com.cn.lxll.dcf.pojo.model.Model;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Project dcf
 * Created on 2024/6/11 下午9:19
 *
 * @author Lxll
 */
@Log4j2
@Service
public class ModelService {
    @Resource
    private ModelDao modelDao;
    @Resource
    private CustomObjectDao customObjectDao;

    public List<Model> findAllModels() {
        return modelDao.findAll();
    }

    public Model findModelById(Long id) {
        return modelDao.findById(id).orElse(null);
    }

    public Model findModelByName(String name) {
        return modelDao.findByName(name).orElse(null);
    }

    public List<Model> findAllByInfosContains(Long infoId) {
        List<Model> models = new ArrayList<>();
        modelDao.findAllByInfosContains(infoId).forEach(model -> models.add(modelDao.findById(model.getId()).orElse(null)));
        return models;
    }

    public List<Model> findAllByInfosContainsAndNameLike(Long infoId, String name) {
        List<Model> models = new ArrayList<>();
        modelDao.findAllByInfosContainsAndNameLike(infoId, name).forEach(model -> models.add(modelDao.findById(model.getId()).orElse(null)));
        return models;
    }

    public Model saveModel(Model model) {
        for (CustomObject customObject : model.getInfos()) {
            if (customObject.getId() == null || customObjectDao.findById(customObject.getId()).orElse(null) == null) {
                log.error("CustomObject {} is null", customObject.getId());
                return null;
            }
        }
        model.setId(null);
        model = modelDao.save(model);
        modelDao.clearInfos(model.getId());
        for (CustomObject customObject : model.getInfos()) {
            modelDao.addInfo(model.getId(), customObject.getId());
        }
        return modelDao.findById(model.getId()).orElse(null);
    }

    public Model updateModel(Model model) {
        for (CustomObject customObject : model.getInfos()) {
            if (customObject.getId() == null || customObjectDao.findById(customObject.getId()).orElse(null) == null) {
                log.error("CustomObject {} is null", customObject.getId());
                return null;
            }
        }
        model = modelDao.save(model);
        return modelDao.findById(model.getId()).orElse(null);
    }

    public void addInfo(Long modelId, Long infId) {
        modelDao.addInfo(modelId, infId);
    }

    public void removeInfo(Long modelId, Long infId) {
        modelDao.removeInfo(modelId, infId);
    }

    public void deleteModel(Long id) {
        modelDao.deleteById(id);
    }
}
