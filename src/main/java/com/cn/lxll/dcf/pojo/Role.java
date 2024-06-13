package com.cn.lxll.dcf.pojo;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

/**
 * Project dcf
 *
 * @author Lxll
 */
@Data
@Node("Role")
public class Role {

    @Id
    @GeneratedValue
    private Long id;

    // 角色名
    @Property
    private String name;

    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
