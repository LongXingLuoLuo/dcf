package com.cn.lxll.dcf.pojo.form;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
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
    protected Long id;

    // 采集项标题
    @Property
    protected String label;

    // 采集项描述
    @Property
    protected String help;

    // 采集项类型
    @Property
    protected String type = "text";

    // 采集项是否必填
    @Property
    protected Boolean required = false;

    // 采集项选项
    @Property
    protected List<String> options;

    // 采集项表单
    @JsonIgnore
    @Relationship(type = "HAS_ITEM", direction = Relationship.Direction.INCOMING)
    protected HasItem hasItem;
}