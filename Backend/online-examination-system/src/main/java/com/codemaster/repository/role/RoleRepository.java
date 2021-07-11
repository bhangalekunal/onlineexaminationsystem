package com.codemaster.repository.role;

import com.codemaster.entity.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends JpaRepository<Role,String> {

    @Query("SELECT r FROM Role r WHERE r.roleId = ?1 and r.status = 'ENABLED'")
    Role findByRoleId(@Param("roleId") String roleId);
}
