package com.cn.lxll.dcf.dao;

import com.cn.lxll.dcf.pojo.model.Model;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;
import java.util.Optional;

/**
 * Project dcf
 * Created on 2024/6/9 下午10:32
 *
 * @author Lxll
 */
public interface ModelDao extends Neo4jRepository<Model, Long> {
    Optional<Model> findByName(String name);

    @Query("MATCH (m:Model)-[:HAS_INFO]->(co:CustomObject) WHERE id(co) = $infoId RETURN m")
    List<Model> findAllByInfosContains(Long infoId);

    @Query("MATCH (m:Model)-[:HAS_INFO]->(co:CustomObject) WHERE id(co) = $infoId AND m.name CONTAINS $name RETURN m")
    List<Model> findAllByInfosContainsAndNameLike(Long infoId, String name);

    @Query("MATCH (m:Model), (o:CustomObject) WHERE id(m)=$modelId AND id(o)=$objectId MERGE (m)-[:HAS_INFO]->(o)")
    void addInfo(Long modelId, Long objectId);

    @Query("MATCH (m:Model)-[r:HAS_INFO]->(co:CustomObject) WHERE id(m) = $modelId AND id(co) = $objectId DELETE r")
    void removeInfo(Long modelId, Long objectId);

    @Query("MATCH (m:Model)-[r:HAS_INFO]->(co:CustomObject) WHERE id(m) = $modelId DELETE r")
    void clearInfos(Long modelId);

    @Query("MATCH (m:Model:User) WHERE id(m) = $modelId RETURN count(m) > 0")
    Boolean isUser(Long modelId);
}