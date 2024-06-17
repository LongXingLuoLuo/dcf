package com.cn.lxll.dcf.pojo.form;

import com.cn.lxll.dcf.pojo.User;
import com.cn.lxll.dcf.pojo.model.CustomObject;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.neo4j.core.schema.*;

import java.time.Instant;

/**
 * Project dcf
 *
 * @author Lxll
 */
@Data
@Node("Form")
public class Form {
    @Id
    @GeneratedValue
    private Long id;

    @Property
    private String title;

    @Property
    private String description;

    @Property
    @CreatedDate
    private Instant createTime;

    @Property
    @LastModifiedDate
    private Instant updateTime;

    @ReadOnlyProperty
    @Relationship(type = "MANAGE", direction = Relationship.Direction.INCOMING)
    private User manager;

    @ReadOnlyProperty
    @Relationship(type = "REF", direction = Relationship.Direction.OUTGOING)
    private CustomObject ref;
}