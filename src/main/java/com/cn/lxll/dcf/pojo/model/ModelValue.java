package com.cn.lxll.dcf.pojo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.neo4j.core.schema.*;

/**
 * Project dcf
 * Created on 2024/6/9 下午10:08
 *
 * @author Lxll
 */
@Data
@Node
public class ModelValue {
    @Id
    @GeneratedValue
    private Long id;

    @Property
    private String content;

//    @JsonIgnore
    @ReadOnlyProperty
    @Relationship(value = "HAS_VALUE", direction = Relationship.Direction.INCOMING)
    private Model owner;

    @ReadOnlyProperty
    @Relationship(value = "HAS_INFO", direction = Relationship.Direction.OUTGOING)
    private CustomProperty info;

    @ReadOnlyProperty
    @Relationship(value = "REF", direction = Relationship.Direction.OUTGOING)
    private Model ref;
}
