package com.cn.lxll.dcf.dao;

import com.cn.lxll.dcf.pojo.model.ModelValue;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

/**
 * Project dcf
 * Created on 2024/6/11 下午8:16
 *
 * @author Lxll
 */
public interface ModelValueDao extends Neo4jRepository<ModelValue, Long>{

    @Query("MATCH (m:Model)-[:HAS_VALUE]->(v:ModelValue)-[:HAS_INFO]->(p:CustomProperty) WHERE id(m) = $modelId AND id(p) = $propertyId RETURN v")
    List<ModelValue> findAllByModelANDProperty(Long modelId, Long propertyId);

    @Query("MATCH (m:Model)-[:HAS_VALUE]->(v:ModelValue) WHERE id(m) = $modelId RETURN r")
    List<ModelValue> findAllByModel(Long modelId);

    @Query("MATCH (v:ModelValue), (p:CustomProperty) WHERE id(v) = $valueId AND id(p) = $infoId CREATE (v)-[:HAS_INFO]->(p)")
    void setInfo(Long valueId, Long infoId);

    @Query("MATCH (v:ModelValue)-[r:HAS_INFO]->(:CustomProperty) WHERE id(v) = $valueId DELETE r")
    void removeInfo(Long valueId);

    @Query("MATCH (v:ModelValue), (m:Model) WHERE id(v) = $valueId AND id(m) = $refId MERGE (v)-[:REF]->(m)")
    void setRef(Long valueId, Long refId);

    @Query("MATCH (v:ModelValue)-[r:HAS_REF]->(:CustomProperty) WHERE id(v) = $valueId DELETE r")
    void removeRef(Long valueId);

    @Query("MATCH (v:ModelValue), (m:Model) WHERE id(v) = $valueId AND id(m) = $ownerId MERGE (m)-[:HAS_VALUE]->(v)")
    void setOwner(Long valueId, Long ownerId);

    @Query("MATCH (m:Model)-[r:HAS_VALUE]->(:Model) WHERE id(m) = $ownerId DELETE r")
    void removeOwner(Long ownerId);

    @Query("MATCH (m:Model)-[:HAS_VALUE]-(v:ModelValue)-[:HAS_INFO]->(p:CustomProperty) WHERE id(m) = $modelId AND id(p) = $propertyId DELETE v")
    void clearValueByModelAndProperty(Long modelId, Long propertyId);
}