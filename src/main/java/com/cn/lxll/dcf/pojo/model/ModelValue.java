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

    // 模型值的内容
    @Property
    private String content;

    // 模型值所属的模型
    @ReadOnlyProperty
    @Relationship(value = "HAS_VALUE", direction = Relationship.Direction.INCOMING)
    private Model owner;

    // 模型值的信息
    @ReadOnlyProperty
    @Relationship(value = "HAS_INFO", direction = Relationship.Direction.OUTGOING)
    private CustomProperty info;

    // 模型值的引用
    @ReadOnlyProperty
    @Relationship(value = "REF", direction = Relationship.Direction.OUTGOING)
    private Model ref;
}
