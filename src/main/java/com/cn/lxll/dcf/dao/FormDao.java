package com.cn.lxll.dcf.dao;

import com.cn.lxll.dcf.pojo.User;
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
    @Query("MATCH (n:Form) WHERE id(n) = $formId SET n.title = $title")
    void updateTitleById(Long formId, String title);

    @Query("MATCH (n:Form) WHERE id(n) = $formId SET n.description = $description")
    void updateDescriptionById(Long formId, String description);

    @Query("MATCH (n:Form) WHERE id(n) = $formId MATCH (u:User) WHERE id(u) = $managerId MATCH ()-[r:MANAGE]->(n) DELETE r CREATE (u)-[:MANAGE]->(n)")
    void updateManagerById(Long formId, Long managerId);

    @Query("MATCH (u:User) WHERE id(u) = $managerId MATCH (u)-[:MANAGE]->(n) RETURN n")
    List<Form> findAllByManagerId(Long managerId);

    @Query("MATCH (u:User) WHERE id(u) = $userId MATCH (u)-[:MANAGE]->(n) WHERE id(n) = $formId RETURN isNaN(n)")
    boolean isManager(Long formId, Long userId);

    @Query("MATCH (f:Form) WHERE id(f) = $formId SET f.updateTime = $updateTime")
    void updateUpdateTimeById(Long formId, Date updateTime);

    @Query("MATCH (f:Form)-[:HAS_ITEM]->(i:FormItem) WHERE id(i) = $formItemId SET f.updateTime = $updateTime")
    void updateUpdateTimeByFormItemId(Long formItemId, Date updateTime);
}
