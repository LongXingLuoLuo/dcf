package com.cn.lxll.dcf.service;

import com.cn.lxll.dcf.dao.RoleDao;
import com.cn.lxll.dcf.dao.UserDao;
import com.cn.lxll.dcf.pojo.Role;
import com.cn.lxll.dcf.pojo.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Project dcf
 *
 * @author Lxll
 */
@Log4j2
@Service
public class UserService implements UserDetailsService {
    @Resource
    private UserDao userDao;

    @Resource
    private RoleDao roleDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.getUserByUsername(username);
    }

    /**
     * 获取用户权限信息（角色、菜单权限）
     *
     * @param userId 用户id
     * @return 用户权限信息
     */
    public Collection<? extends GrantedAuthority> getUserAuthority(Long userId) {
        // 实际怎么写以数据表结构为准，这里只是写个例子
        // 角色(比如ROLE_admin)，菜单操作权限(比如sys:user:list)
        User user = userDao.findById(userId).orElse(null);

        if (user != null) {
            return user.getAuthorities();
        } else {
            return Collections.emptyList();
        }
    }

    public User addUser(User user) {
        if (user == null || userDao.existsByUsername(user.getUsername())) {
            return null;
        }
        user.setId(null);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userDao.save(user);
    }

    public void updateUsername(User user) {
        if (user == null || user.getId() == null) {
            return;
        }
        userDao.updateUsernameById(user.getId(), user.getUsername());
    }

    public void updatePassword(User user) {
        if (user == null || user.getId() == null) {
            return;
        }
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userDao.updatePasswordById(user.getId(), user.getPassword());
    }

    public void addRole(User user, Role role) {
        if (user == null || user.getId() == null || role == null || role.getId() == null) {
            return;
        }
        userDao.hasRoleById(user.getId(), role.getId());
    }

    public void removeRole(User user, Role role) {
        if (user == null || role == null) {
            return;
        }
        userDao.removeHasRoleById(user.getId(), role.getId());
    }

    /**
     * 根据用户名获取用户，若无则为空
     * @param username 用户名
     * @return 用户
     */
    public User getUserByUsername(String username) {
        return userDao.findByUsername(username);
    }

    /**
     * 根据id获取用户
     * @param id 用户id
     * @return 用户
     */
    public User getUserById(Long id) {
        return userDao.findById(id).orElse(null);
    }

    /**
     * 根据表单id获取管理员用户
     * @param formId 表单id
     * @return 用户
     */
    public User getManagerByFormId(Long formId) {
        return userDao.findByFormId(formId);
    }

    /**
     * 获取所有用户
     * @return 所有用户
     */
    public Iterable<User> getAllUsers() {
        return userDao.findAll();
    }

    /**
     * 根据用户名判断用户是否存在
     * @param username 用户名
     * @return 是否存在
     */
    public boolean existsByUsername(String username) {
        return userDao.existsByUsername(username);
    }

    /**
     * 根据id删除用户
     * @param id 用户id
     */
    public void deleteUserById(Long id) {
        userDao.deleteById(id);
    }
}
