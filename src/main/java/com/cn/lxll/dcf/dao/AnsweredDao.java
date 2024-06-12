package com.cn.lxll.dcf.dao;

import com.cn.lxll.dcf.pojo.form.Answered;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.Optional;

/**
 * Project dcf
 *
 * @author Lxll
 */
public interface AnsweredDao extends Neo4jRepository<Answered, Long> {

    @Query("MATCH (u:User), (f:FormItem) WHERE id(u) = $userId AND id(f) = $formItemId MERGE (u)-[r:ANSWERED]->(f) SET r.content = $content RETURN id(r)")
    Integer saveContentByUserIdAndFormItemId(Long userId, Long formItemId, String content);

    @Query("MATCH (u:User)-[r:ANSWERED]->(f:FormItem) WHERE id(u) = $userId AND id(f) = $formItemId RETURN r.content")
    Optional<String> findContentByUserIdAndFormItemId(Long userId, Long formItemId);

    @Query("MATCH (u:User)-[r:ANSWERED]->(f:FormItem) WHERE id(r) = $id RETURN r{.content, .id}")
    Optional<Answered> findByAnsweredId(Long id);

    @Query("MATCH (u:User)-[r:ANSWERED]->(f:FormItem) WHERE id(u) = $userId AND id(f) = $formItemId RETURN r{.content, .id}")
    Optional<Answered> findByUserIdAndFormItemId(Long userId, Long formItemId);

    @Query("MATCH (u:User)-[r:ANSWERED]->(f:FormItem) WHERE id(u) = $userId AND id(f) = $formItemId RETURN count(r) > 0")
    boolean existsByUserIdAndFormItemId(Long userId, Long formItemId);

    @Query("MATCH (u:User)-[r:ANSWERED]->(f:FormItem) WHERE id(u) = $userId AND id(f) = $formItemId DELETE r")
    void deleteByUserIdAndFormItemId(Long userId, Long formItemId);

    @Query("MATCH (ri:FormItem) WHERE id(ri) = $relatedFormItemId MATCH (u:User) WHERE id(u) = userId MATCH (u)-[a:ANSWERED]->(i:FormItem) WHERE i.type=ri.type AND i.label = ri.label RETURN a.content")
    Optional<String> findByRelatedFormItemId(Long userId, Long relatedFormItemId);

//    @Query("MATCH (n1), (n2) WHERE id(n)=$id1 AND id(n2)=$id2 MERGE (n1)-[r:$rLabel]->(n2) RETURN r.content")
//    Optional<String> findByRelatedFormItemId(Long id1, Long id2);
}
