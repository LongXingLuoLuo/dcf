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
        return userDao.findByForm(formId);
    }

    /**
     * 获取所有用户
     * @return 所有用户
     */
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    public User save(User user) {
        if (user == null || userDao.existsByUsername(user.getUsername())) {
            return null;
        }
        user.setId(null);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        return userDao.save(user);
    }

    public User saveWithRoles(User user, List<Role> roles) {
        if (user == null || userDao.existsByUsername(user.getUsername())) {
            return null;
        }
        for (Role role : roles) {
            if (!roleDao.findById(role.getId()).isPresent()) {
                return null;
            }
        }
        user.setId(null);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user = userDao.save(user);
        for (Role role : roles) {
            userDao.addRole(user.getId(), role.getId());
        }
        return userDao.findById(user.getId()).orElse(null);
    }

    public void updateUsername(Long userId, String username) {
        userDao.updateUsername(userId, username);
    }

    public void updatePassword(Long userId, String password) {
        userDao.updatePassword(userId, new BCryptPasswordEncoder().encode(password));
    }

    public void addRole(Long userId, Long roleId) {
        userDao.addRole(userId, roleId);
    }

    public void removeRole(Long userId, Long roleId) {
        userDao.removeRole(userId, roleId);
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
