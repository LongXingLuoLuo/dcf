package com.cn.lxll.dcf.service;

import com.cn.lxll.dcf.dao.CustomObjectDao;
import com.cn.lxll.dcf.pojo.model.CustomObject;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Project dcf
 * Created on 2024/6/10 上午1:28
 *
 * @author Lxll
 */
@Log4j2
@Service
public class CustomObjectService {
    @Resource
    private CustomObjectDao customObjectDao;

    public List<CustomObject> findAllCustomObjects() {
        return customObjectDao.findAll();
    }

    public CustomObject findCustomObjectById(Long id) {
        return customObjectDao.findById(id).orElse(null);
    }

    public CustomObject findCustomObjectByName(String name) {
        return customObjectDao.findByName(name).orElse(null);
    }

    public CustomObject findCustomObjectByType(String type) {
        return customObjectDao.findByType(type).orElse(null);
    }

    public CustomObject saveCustomObject(CustomObject customObject) {
        customObject.setId(null);
        return customObjectDao.save(customObject);
    }

    public void updateCustomObject(CustomObject customObject) {
        customObjectDao.save(customObject);
    }

    public void deleteCustomObject(Long id) {
        customObjectDao.deleteById(id);
    }

}
