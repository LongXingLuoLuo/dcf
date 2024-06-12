package com.cn.lxll.dcf.pojo.form;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

/**
 * Project dcf
 *
 * @author Lxll
 */
@Data
@RelationshipProperties
public class HasItem {

    @RelationshipId
    @GeneratedValue
    private Long id;

    private Integer order;

    @TargetNode
    private Form form;
}
