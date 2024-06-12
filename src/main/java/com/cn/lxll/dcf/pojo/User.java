package com.cn.lxll.dcf.pojo;

import com.cn.lxll.dcf.pojo.model.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.neo4j.core.schema.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.*;

/**
 * Project dcf
 *
 * @author Lxll
 */
@Data
@Node("User")
public class User implements UserDetails{
    @Id
    @GeneratedValue
    private Long id;

    // 用户名
    @Property
    private String username;

    // 密码
    @Property
    private String password;

    // 表示用户是否没有过期
    @Property
    private boolean accountNonExpired;

    // 账户是否没有被锁定
    @Property
    private boolean accountNonLocked;

    // 密码是否没有过期
    @Property
    private boolean credentialsNonExpired;

    // 账户是否可用
    @Property
    private boolean enabled;

    // 用户角色
    @Relationship("HAS_ROLE")
    private List<Role> roles;

    public User(){
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
    }

    /**
     * 返回用户的角色信息
     * @return 用户的角色信息
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    /**
     * 用户是否没有过期
     * @return 用户是否没有过期
     */
    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    /**
     * 账户是否没有被锁定
     * @return 账户是否没有被锁定
     */
    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    /**
     * 密码是否没有过期
     * @return 密码是否没有过期
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    /**
     * 密码是否可用
     * @return 密码是否可用
     */
    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
