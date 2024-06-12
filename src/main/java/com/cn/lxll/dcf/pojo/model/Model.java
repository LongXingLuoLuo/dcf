package com.cn.lxll.dcf.pojo.model;

import lombok.Data;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.neo4j.core.schema.*;

import java.util.List;

/**
 * Project dcf
 * Created on 2024/6/9 下午10:22
 *
 * @author Lxll
 */
@Data
@Node
public class Model {
    @Id
    @GeneratedValue
    private Long id;

    @Property
    private String name;

    @ReadOnlyProperty
    @Relationship(value = "HAS_INFO", direction = Relationship.Direction.OUTGOING)
    private List<CustomObject> infos;
}