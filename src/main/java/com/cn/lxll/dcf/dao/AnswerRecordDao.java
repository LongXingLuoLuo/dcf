package com.cn.lxll.dcf.dao;

import com.cn.lxll.dcf.pojo.form.AnswerRecord;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

/**
 * Project dcf
 *
 * @author Lxll
 */
public interface AnswerRecordDao extends Neo4jRepository<AnswerRecord, Long> {

    @Query("MATCH (u:User)-[r:ANSWERED]->(i:FormItem) WHERE id(i) = $formItemId RETURN {content: r.content, userId: id(u), formItemId: id(i)} AS Result")
    List<AnswerRecord> findAllContentByFormItemId(Long formItemId);

    @Query("MATCH (u:User)-[r:ANSWERED]->(i:FormItem)<-[:HAS_ITEM]-(f:Form) WHERE id(f) = $formId RETURN {content: r.content, userId: id(u), formItemId: id(i)} AS Result")
    List<AnswerRecord> findByFormId(Long formId);
}
