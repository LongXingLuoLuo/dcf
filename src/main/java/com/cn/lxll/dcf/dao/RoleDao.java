package com.cn.lxll.dcf.dao;

import com.cn.lxll.dcf.pojo.Role;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

/**
 * Project dcf
 *
 * @author Lxll
 */
public interface RoleDao extends Neo4jRepository<Role, Long> {

    Role findByName(String name);

    @Query("MATCH (n:Role) WHERE id(n) = $roleId SET n.name = $name")
    void updateNameById(Long roleId, String name);

    @Query("MERGE (r:Role{name:'user'}) RETURN r")
    Role findUser();

    Boolean existsByName(String name);
}
