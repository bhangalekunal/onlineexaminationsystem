package com.codemaster.service.role;

import com.codemaster.entity.role.Role;
import com.codemaster.exceptionhandler.domain.NoActiveRoleFoundException;
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

    public Role getRoleByRoleId(String roleId) throws NoActiveRoleFoundException {
        Role role = roleRepository.findByRoleId(roleId);
        if (role == null)
        {
            throw new NoActiveRoleFoundException("No Active Role Found with RoleId: "+roleId);
        }
        return role;
    }

    public Role addRole(Role role)
    {
        return roleRepository.save(role);
    }


    public Role getRoleForGrantAuthorityByRoleId(String roleId){
        System.out.println("DEBUG ROLE--->");
        Role role = roleRepository.findByRoleId(roleId);
        return role;
    }
}
