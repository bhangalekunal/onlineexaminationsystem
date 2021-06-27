package com.codemaster.repository.department;

import com.codemaster.entity.department.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, String> {
    @Query("SELECT d FROM Department d WHERE d.departmentCode = ?1 and d.status = 'ENABLED'")
    List<Department> findByDepartmentCode(@Param("departmentCode") String departmentCode);
}
