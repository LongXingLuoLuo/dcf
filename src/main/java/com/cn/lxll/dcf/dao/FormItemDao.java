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

    @Query("MATCH (i:FormItem), (f:Form) WHERE id(i) = $formItemId AND id(f) = $formId MERGE (f)-[:HAS_ITEM]->(i)")
    void setFormById(Long formItemId, Long formId);

    @Query("MATCH (u:User)-[r:ANSWERED]->(f:FormItem) WHERE id(r) = $answeredId RETURN f")
    FormItem findByAnsweredId(Long answeredId);

    @Query("MATCH (f:Form)-[HAS_ITEM]->(i:FormItem) WHERE id(f) = $formId RETURN max(i.order)")
    Integer maxOrderByFormId(Long formId);

    @Query("MATCH (f:Form)-[HAS_ITEM]->(i:FormItem) WHERE id(f) = $formId DETACH DELETE i")
    void deleteAllByFormId(Long formId);

    @Query("MATCH (i:FormItem), (p:CustomProperty) WHERE id(i) = $formItemId AND id(p) = $refId MERGE (i)-[:REF]->(p)")
    void setRef(Long formItemId, Long refId);

    @Query("MATCH (i:FormItem)-[r:REF]->(:CustomProperty) DELETE r")
    void removeRef(Long formItemId);

    @Query("MATCH (i:FormItem), (o:CustomObject) WHERE id(i) = $formItemId AND id(o) = $objectId MERGE (i)-[:OBJECT_TYPE]->(o)")
    void setObject(Long formItemId, Long objectId);

    @Query("MATCH (i:FormItem)-[r:OBJECT_TYPE]->(:CustomObject) WHERE id(i)=$formItemId DELETE r")
    void removeObject(Long formItemId);
}
