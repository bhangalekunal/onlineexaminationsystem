package com.codemaster.service.department;

import com.codemaster.entity.department.Department;
import com.codemaster.exceptionhandler.EntityNotFoundException;
import com.codemaster.repository.department.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;

    public List<Department> getAllDepartments()
    {
        return departmentRepository.findAll();
    }

    public Department getDepartmentByDepartmentCode(String departmentCode)
    {
        return departmentRepository.findByDepartmentCode(departmentCode);
    }


    public Department addDepartment(Department department)
    {
        return departmentRepository.save(department);
    }

    public Department updateDepartment(String departmentCode, Department departmentDetails) throws EntityNotFoundException
    {
        Department department = departmentRepository.findByDepartmentCode(departmentCode);
        if(department == null)
        {
            throw new EntityNotFoundException("Department not found for departmentCode: "+departmentCode);
        }
        department.setDepartmentName(departmentDetails.getDepartmentName());
        department.setDecription(departmentDetails.getDecription());
        department.setStatus(departmentDetails.getStatus());

        final Department updatedDepartment = departmentRepository.save(department);
        return updatedDepartment;
    }

    public Map<String, Boolean> deleteDepartment(String departmentCode) throws EntityNotFoundException
    {
        Department department = departmentRepository.findByDepartmentCode(departmentCode);
        if(department == null)
        {
            throw new EntityNotFoundException("Department not found for departmentCode: "+departmentCode);
        }
        departmentRepository.delete(department);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}
