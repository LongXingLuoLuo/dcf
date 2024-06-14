package com.cn.lxll.dcf.pojo.form;

import com.cn.lxll.dcf.pojo.User;
import com.cn.lxll.dcf.pojo.model.Model;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.neo4j.core.schema.*;

/**
 * Project dcf
 *
 * @author Lxll
 */
@Data
@Node
public class Answer {
    @Id
    @GeneratedValue
    private Long id;

    @ReadOnlyProperty
    @Relationship(type = "ANSWERED", direction = Relationship.Direction.INCOMING)
    private User user;

    @ReadOnlyProperty
    @Relationship(type = "HAS_ANSWER", direction = Relationship.Direction.INCOMING)
    private FormItem formItem;

    @Property
    private String content;

    @ReadOnlyProperty
    @Relationship(type = "REF", direction = Relationship.Direction.OUTGOING)
    private Model ref;
}
