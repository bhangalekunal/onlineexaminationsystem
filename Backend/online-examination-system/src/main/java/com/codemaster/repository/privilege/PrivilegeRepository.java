package com.codemaster.repository.privilege;

import com.codemaster.entity.privilege.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, String> {

    /*
    @Query("SELECT p FROM Privilege p WHERE p.roleId = ?1")
    List<Privilege> findPriviligeByRoleId(@Param("roleId") String roleId);
     */
}
