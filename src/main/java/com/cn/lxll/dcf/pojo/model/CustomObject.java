package com.cn.lxll.dcf.pojo.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

/**
 * Project dcf
 * 自定义模型
 *
 * @author Lxll
 */
@Data
@Node("CustomObject")
public class CustomObject {
    @Id
    @GeneratedValue
    private Long id;

    @Property
    private String name;    // 名称

    @Property
    private String description; // 描述

    @Property
    private String type;    // 类型
}
