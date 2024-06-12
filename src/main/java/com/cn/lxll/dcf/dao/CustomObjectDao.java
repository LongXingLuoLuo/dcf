package com.cn.lxll.dcf.dao;

import com.cn.lxll.dcf.pojo.model.CustomObject;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.Optional;

/**
 * Project dcf
 * Created on 2024/6/9 下午11:03
 *
 * @author Lxll
 */
public interface CustomObjectDao extends Neo4jRepository<CustomObject, Long> {

    Optional<CustomObject> findByName(String name);

    Optional<CustomObject> findByType(String type);
}
