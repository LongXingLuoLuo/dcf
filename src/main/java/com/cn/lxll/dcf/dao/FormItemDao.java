package com.cn.lxll.dcf.dao;

import com.cn.lxll.dcf.pojo.form.FormItem;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

/**
 * Project dcf
 *
 * @author Lxll
 */
public interface FormItemDao extends Neo4jRepository<FormItem, Long> {

    @Query("MATCH (n:Form)-[:HAS_ITEM]->(m:FormItem) WHERE id(n) = $formId RETURN m")
    List<FormItem> findAllByFormId(Long formId);

    @Query("MATCH (n:FormItem) WHERE id(n) = $formItemId SET n.label = $label")
    void updateLabelById(Long formItemId, String label);

    @Query("MATCH (n:FormItem) WHERE id(n) = $formItemId SET n.help = $help")
    void updateHelpById(Long formItemId, String help);

    @Query("MATCH (n:FormItem) WHERE id(n) = $formItemId SET n.type = $type")
    void updateTypeById(Long formItemId, String type);

    @Query("MATCH (n:FormItem) WHERE id(n) = $formItemId SET n.required = $required")
    void updateRequiredById(Long formItemId, boolean required);

    @Query("MATCH (i:FormItem), (f:Form) WHERE id(i) = $formItemId AND id(f) = $formId MERGE (f)-[:HAS_ITEM]->(i)")
    void updateFormById(Long formItemId, Long formId);

    @Query("MATCH (n:FormItem) WHERE id(n) = $formItemId SET n.options = $options")
    void updateOptionsById(Long formItemId, List<String> options);

    @Query("MATCH (u:User)-[r:ANSWERED]->(f:FormItem) WHERE id(r) = $answeredId RETURN f")
    FormItem findByAnsweredId(Long answeredId);
}
