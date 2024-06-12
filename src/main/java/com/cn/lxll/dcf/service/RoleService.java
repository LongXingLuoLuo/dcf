package com.cn.lxll.dcf.service;

import com.cn.lxll.dcf.dao.RoleDao;
import com.cn.lxll.dcf.pojo.Role;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Project dcf
 *
 * @author Lxll
 */
@Log4j2
@Service
public class RoleService {
    @Resource
    private RoleDao roleDao;

    public Role addRole(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }
        Role role = roleDao.findByName(name);
        if (role != null) {
            return role;
        }
        role = new Role();
        role.setName(name);
        return roleDao.save(role);
    }

    public Role updateName(Role role) {
        if (role == null || role.getId() == null || role.getName() == null || role.getName().isEmpty() || !roleDao.existsById(role.getId()) || roleDao.existsByName(role.getName())) {
            return null;
        }
        roleDao.updateNameById(role.getId(), role.getName());
        return roleDao.findById(role.getId()).orElse(null);
    }

    public Role getRoleById(Long id) {
        return roleDao.findById(id).orElse(null);
    }

    public Role getRoleByName(String name) {
        return roleDao.findByName(name);
    }

    public Iterable<Role> getAllRoles() {
        return roleDao.findAll();
    }

    public void deleteRoleById(Long id) {
        roleDao.deleteById(id);
    }

    public void deleteRoleByName(String name) {
        Role role = roleDao.findByName(name);
        if (role != null) {
            roleDao.deleteById(role.getId());
        }
    }
}
