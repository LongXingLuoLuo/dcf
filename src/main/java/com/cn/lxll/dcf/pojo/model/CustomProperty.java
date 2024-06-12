package com.cn.lxll.dcf.pojo.model;

import lombok.Data;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.neo4j.core.schema.*;

/**
 * Project dcf
 * Created on 2024/6/8 下午3:10
 *
 * @author Lxll
 */
@Data
@Node("CustomProperty")
public class CustomProperty {

    @Id
    @GeneratedValue
    private Long id;

    @Property
    private String key;

    /**
     * String, Integer, Boolean, Object, Array
     */
    @Property
    private String type;

    @Property
    private boolean arr;

    @ReadOnlyProperty
    @Relationship(value = "REF", direction = Relationship.Direction.OUTGOING)
    private CustomObject ref;
}
