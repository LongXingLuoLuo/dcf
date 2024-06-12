package com.cn.lxll.dcf.pojo.form;

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
@Node
public class AnswerRecord {
    @Id
    @GeneratedValue
    private Long id;

    @Property
    private Long userId;

    @Property
    private Long formItemId;

    @Property
    private String content;
}
