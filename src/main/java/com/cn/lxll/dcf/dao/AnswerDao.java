package com.cn.lxll.dcf.dao;

import com.cn.lxll.dcf.pojo.form.Answer;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;
import java.util.Optional;

/**
 * Project dcf
 * Created on 2024/6/13 下午10:20
 *
 * @author Lxll
 */
public interface AnswerDao extends Neo4jRepository<Answer, Long>{
    @Query("MATCH (a:Answer), (m:Model) WHERE id(a) = $answerId AND id(m) = $modelId MERGE (a)-[:REF]->(m)")
    void setRef(Long answerId, Long modelId);

    @Query("MATCH (a:Answer)-[r:REF]->(:Model) WHERE id(a) = $id DELETE r")
    void removeRef(Long id);

    @Query("MATCH (a:Answer), (i:FormItem) WHERE id(a) = $answerId AND id(i) = $formItemId MERGE (a)<-[:HAS_ANSWER]-(i)")
    void setFormItem(Long answerId, Long formItemId);

    @Query("MATCH (a:Answer)<-[r:HAS_ANSWER]-(:FormItem) WHERE id(a) = $id DELETE r")
    void removeFormItem(Long id);

    @Query("MATCH (a:Answer), (u:User) WHERE id(a) = $answerId AND id(u) = $userId MERGE (a)<-[:ANSWERED]-(u)")
    void setUser(Long answerId, Long userId);

    @Query("MATCH (a:Answer)<-[r:ANSWERED]-(:User) WHERE id(a) = $id DELETE r")
    void removeUser(Long id);

    @Query("MATCH (u:User)-[:ANSWERED]->(a:Answer)<-[:HAS_ANSWER]-(i:FormItem) WHERE id(u) = $userId AND id(i) = $formItemId RETURN a")
    List<Answer> findAllByUserAndFormItem(Long userId, Long formItemId);

    @Query("MATCH (u:User)-[:ANSWERED]->(a:Answer)<-[:HAS_ANSWER]-(i:FormItem) WHERE id(u) = $userId AND id(i) = $formItemId DELETE a")
    void deleteByUserAndFormItem(Long userId, Long formItemId);

    @Query("MATCH (a:Answer)<-[:HAS_ANSWER]-(i:FormItem) WHERE id(i) = $formItemId RETURN a")
    List<Answer> findAllByFormItem(Long formItemId);

    @Query("MATCH (a:Answer)<-[:HAS_ANSWER]-(:FormItem)<-[:HAS_ITEM]-(f:Form) WHERE id(f) = $formId RETURN a")
    List<Answer> findAllByForm(Long formId);
}
