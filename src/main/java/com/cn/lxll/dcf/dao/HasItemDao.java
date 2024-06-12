package com.cn.lxll.dcf.dao;

import com.cn.lxll.dcf.pojo.form.HasItem;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

/**
 * Project dcf
 *
 * @author Lxll
 */
public interface HasItemDao extends Neo4jRepository<HasItem, Long> {
    @Query("MATCH (n:Form)-[r:HAS_ITEM]-() WHERE id(n) = $formId RETURN max(r.order)")
    Integer maxOrderByFormId(Long formId);
}
