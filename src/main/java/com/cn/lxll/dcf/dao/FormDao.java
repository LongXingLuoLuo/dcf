package com.cn.lxll.dcf.dao;

import com.cn.lxll.dcf.pojo.form.Form;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

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

    @Query("MATCH (u:User) WHERE id(u) = $managerId MATCH (u)-[:MANAGE]->(f:Form) RETURN f")
    List<Form> findAllByManagerId(Long managerId);

    @Query("MATCH (u:User)-[:MANAGE]->(f:Form) WHERE id(u) = $userId AND id(f) = $formId RETURN isNaN(f)")
    boolean isManager(Long formId, Long userId);

    @Query("MATCH (f:Form) WHERE id(f) = $formId SET f.updateTime = $updateTime")
    void updateUpdateTimeById(Long formId, Date updateTime);

    @Query("MATCH (f:Form)-[:HAS_ITEM]->(i:FormItem) WHERE id(i) = $formItemId SET f.updateTime = $updateTime")
    void updateUpdateTimeByFormItemId(Long formItemId, Date updateTime);
}
