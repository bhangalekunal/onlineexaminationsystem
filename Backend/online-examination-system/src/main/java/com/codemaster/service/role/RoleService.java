package com.codemaster.service.role;

import com.codemaster.entity.role.Role;
import com.codemaster.repository.role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role getRoleByRoleId(String roleId)
    {
        Role role = roleRepository.findByRoleId(roleId);
        return role;
    }

    public Role addRole(Role role)
    {
        return roleRepository.save(role);
    }
}
