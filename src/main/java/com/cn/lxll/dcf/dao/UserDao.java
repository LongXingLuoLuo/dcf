package com.cn.lxll.dcf.dao;

import com.cn.lxll.dcf.pojo.Role;
import com.cn.lxll.dcf.pojo.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.Iterator;
import java.util.List;

/**
 * Project dcf
 *
 * @author Lxll
 */
public interface UserDao extends Neo4jRepository<User, Long> {

    @Query("MATCH (n:User) WHERE id(n) = $userId MATCH (m:Role) WHERE id(m) = $roleId CREATE (n)-[:HAS_ROLE]->(m)")
    void hasRoleById(@Param("userId") Long userId, @Param("roleId") Long roleId);

    @Query("MATCH (n:User)-[r:HAS_ROLE]-() WHERE id(n) = $userId DELETE r")
    void hasNoRole(@Param("userId") Long userId);

    @Query("MATCH (n:User)-[r:HAS_ROLE]->(m:Role) WHERE id(n) = $userId AND id(m) = $roleId DELETE r")
    void removeHasRoleById(@Param("userId") Long userId, @Param("roleId") Long roleId);

    User findByUsername(String username);

    @Query("MATCH (n:User) WHERE id(n) = $userId SET n.username = $username")
    void updateUsernameById(Long userId, String username);

    @Query("MATCH (n:User) WHERE id(n) = $userId SET n.password = $password")
    void updatePasswordById(Long userId, String password);

    Boolean existsByUsername(String username);

    @Query("MATCH (u:User)-[r:ANSWERED]->(f:FormItem) WHERE id(r) = $answeredId RETURN u")
    User findByAnsweredId(Long answeredId);

    @Query("MATCH (u:User)-[r:MANAGE]->(f:Form) WHERE id(f) = $formId RETURN u")
    User findByFormId(Long formId);
}
