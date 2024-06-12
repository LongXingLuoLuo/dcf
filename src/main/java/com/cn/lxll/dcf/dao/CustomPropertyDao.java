package com.cn.lxll.dcf.dao;

import com.cn.lxll.dcf.pojo.model.CustomProperty;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;
import java.util.Optional;

/**
 * Project dcf
 * Created on 2024/6/8 下午3:42
 *
 * @author Lxll
 */
public interface CustomPropertyDao extends Neo4jRepository<CustomProperty, Long> {

    @Query("MATCH (c:CustomObject)-[r:HAS_PROPERTY]->(p:CustomProperty) WHERE id(c) = $customObjectId RETURN p")
    List<CustomProperty> findAllByCustomObject(Long customObjectId);

    @Query("MATCH (m:Model)-[:HAS_INFO]->(:CustomObject)-[:HAS_PROPERTY]->(p:CustomProperty) WHERE id(m) = $modelId RETURN p")
    List<CustomProperty> findAllByModel(Long modelId);

    @Query("MATCH (c:CustomObject)-[r:HAS_PROPERTY]->(p:CustomProperty) WHERE id(c) = $customObjectId AND p.key = $key RETURN p")
    Optional<CustomProperty> findByCustomObjectAndKey(Long customObjectId, String key);

    @Query("MATCH (c:CustomObject), (p:CustomProperty) WHERE id(c) = $customObjectId AND id(p) = $customPropertyId MERGE (c)-[:HAS_PROPERTY]->(p)")
    void addProperty(Long customObjectId, Long customPropertyId);

    @Query("MATCH (c:CustomObject)-[r:HAS_PROPERTY]->(p:CustomProperty) WHERE id(c) = $customObjectId AND id(p) = $customPropertyId DELETE r")
    void removeProperty(Long customObjectId, Long customPropertyId);

    @Query("MATCH (p:CustomProperty), (c:CustomObject) WHERE id(p) = $customPropertyId AND id(c) = $refId MERGE (p)-[:REF]->(c)")
    void setRef(Long customPropertyId, Long refId);

    @Query("MATCH (p:CustomProperty)-[r:REF]->(:CustomObject) WHERE id(p) = $customPropertyId DELETE r")
    void removeRef(Long customPropertyId);
}
