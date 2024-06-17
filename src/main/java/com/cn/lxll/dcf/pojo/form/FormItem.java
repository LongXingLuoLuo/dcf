package com.cn.lxll.dcf.pojo.form;

import com.cn.lxll.dcf.pojo.model.CustomObject;
import com.cn.lxll.dcf.pojo.model.CustomProperty;
import lombok.Data;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.neo4j.core.schema.*;

import java.util.List;

/**
 * Project dcf
 *
 * @author Lxll
 */
@Data
@Node(labels = {"FormItem"})
public class FormItem {
    @Id
    @GeneratedValue
    private Long id;

    // 表单项排序
    @Property
    private Integer order;

    // 表单项标题
    @Property
    private String label;

    // 表单项类型
    @Property
    private String type;

    // 表单项选项
    @Property
    private List<String> options;

    // 表单项是否多选
    @Property
    private Boolean multiple;

    @Property
    private String parser;

    @Property
    private String formatter;

    // 表单项关联的自定义属性
    @ReadOnlyProperty
    @Relationship(type = "REF", direction = Relationship.Direction.OUTGOING)
    private CustomProperty ref;

    // 表单项关联的表单
    @ReadOnlyProperty
    @Relationship(type = "HAS_ITEM", direction = Relationship.Direction.INCOMING)
    private Form form;

    @ReadOnlyProperty
    @Relationship(type = "OBJECT_TYPE", direction = Relationship.Direction.OUTGOING)
    private CustomObject object;
}