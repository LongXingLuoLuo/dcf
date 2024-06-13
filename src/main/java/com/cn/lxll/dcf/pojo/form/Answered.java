package com.cn.lxll.dcf.pojo.form;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

/**
 * Project dcf
 *
 * @author Lxll
 */
@Data
@RelationshipProperties
public class Answered {
    @RelationshipId
    @GeneratedValue
    private Long id;

    // 表单结果内容
    @Property
    private String content;

    // 表单结果对应的表单项
    @JsonIgnore
    @TargetNode
    private FormItem formItem;
}
