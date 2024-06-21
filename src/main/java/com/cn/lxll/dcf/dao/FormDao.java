package com.cn.lxll.dcf.dao;

import com.cn.lxll.dcf.pojo.form.Form;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.time.Instant;
import java.util.Date;
import java.util.List;

/**
 * Project dcf
 *
 * @author Lxll
 */
public interface FormDao extends Neo4jRepository<Form, Long> {
    @Query("MATCH (f:Form), (u:User) WHERE id(f) = $formId AND id(u) = $managerId MERGE (u)-[:MANAGE]->(f)")
    void setManager(Long formId, Long managerId);

    @Query("MATCH (f:Form) WHERE id(f) = $formId MATCH (:User)-[r:MANAGE]->(f) DELETE r")
    void removeManager(Long formId);

    @Query("MATCH (f:Form), (o:CustomObject) WHERE id(f) = $formId AND id(o) = $refId MERGE (f)-[:REF]->(o)")
    void setRef(Long formId, Long refId);

    @Query("MATCH (f:Form) WHERE id(f) = $formId MATCH (f)-[r:REF]->(:CustomObject) DELETE r")
    void removeRef(Long formId);

    @Query("MATCH (u:User) WHERE id(u) = $managerId MATCH (u)-[:MANAGE]->(f:Form) RETURN f")
    List<Form> findAllByManagerId(Long managerId);

    @Query("MATCH (u:User)-[:MANAGE]->(f:Form) WHERE id(u) = $userId AND id(f) = $formId RETURN isNaN(f)")
    boolean isManager(Long formId, Long userId);

    @Query("MATCH (f:Form) WHERE id(f) = $formId SET f.updateTime = $updateTime")
    void updateUpdateTimeById(Long formId, Instant updateTime);

    @Query("MATCH (f:Form)-[:HAS_ITEM]->(i:FormItem) WHERE id(i) = $formItemId SET f.updateTime = $updateTime")
    void updateUpdateTimeByFormItemId(Long formItemId, Instant updateTime);

    @Query("MATCH (u:User), (f:Form) WHERE id(u) = $userId AND id(f) = $formId MERGE (u)-[v:HAS_VISITED]->(f) SET v.time = $visitTime")
    void updateVisited(Long userId, Long formId, Date visitTime);

    @Query("MATCH (u:User)-[:HAS_INFO]->(:CustomObject)<-[:REF]-(f:Form) WHERE id(u) = $userId RETURN f UNION MATCH (f:Form) WHERE NOT (:CustomObject)<-[:REF]-(f) RETURN f")
    List<Form> findAllVisitableForms(Long userId);
    
    @Query("MATCH (u:User)-[v:HAS_VISITED]->(f:Form) WHERE id(u) = $userId AND id(f) = $formId RETURN (count(v) > 0)")
    Boolean isVisited(Long userId, Long formId);

    @Query("MATCH (u:User), (f:Form) WHERE id(u) = $userId AND id(f) = $formId AND ((u)-[:HAS_INFO]->(:CustomObject)<-[:REF]-(f) or NOT (:CustomObject)<-[:REF]-(f)) RETURN count(f) > 0")
    Boolean isVisitable(Long userId, Long formId);

    @Query("MATCH (u:User) WHERE id(u) = $userId MATCH (f:Form) WHERE ((u)-[:HAS_INFO]->(:CustomObject)<-[:REF]-(f) or NOT (:CustomObject)<-[:REF]-(f)) AND NOT (u)-[:HAS_VISITED]-(f)  RETURN count(f)")
    Integer countAllNonVisitForms(Long userId);
}
