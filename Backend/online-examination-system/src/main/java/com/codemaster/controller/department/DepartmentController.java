package com.codemaster.controller.department;

import com.codemaster.entity.department.Department;
import com.codemaster.exceptionhandler.EntityNotFoundException;
import com.codemaster.exceptionhandler.InvalidInputException;
import com.codemaster.service.department.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @GetMapping("/departments")
    public ResponseEntity<List<Department>> getAllDepartments()
    {
        return ResponseEntity.ok().body(departmentService.getAllDepartments());
    }

    @GetMapping("/departments/{departmentCode}")
    public ResponseEntity<Department> getDepartmentByDepartmentCode(@PathVariable("departmentCode") String departmentCode) throws EntityNotFoundException,InvalidInputException
    {
        if(departmentCode == null || departmentCode.trim().equals(""))
        {
            throw new InvalidInputException("departmentCode is not a valid");
        }

        Department department = departmentService.getDepartmentByDepartmentCode(departmentCode);
        if(department == null)
        {
            throw new EntityNotFoundException("Department not found for departmentCode: "+departmentCode);
        }
        return ResponseEntity.ok().body(department);
    }

    @PostMapping("/departments")
    public ResponseEntity<Department> createDepartment(@Valid @RequestBody Department department)
    {
        return ResponseEntity.ok().body(departmentService.addDepartment(department));
    }

    @PutMapping("/departments/{departmentCode}")
    public ResponseEntity<Department> updateDepartment(@PathVariable("departmentCode") String departmentCode, @Valid @RequestBody Department department) throws EntityNotFoundException,InvalidInputException
    {
        if(departmentCode == null || departmentCode.trim().equals(""))
        {
            throw new InvalidInputException("departmentCode is not a valid");
        }

        Department updatedDepartment = departmentService.updateDepartment(departmentCode,department);
        return ResponseEntity.ok().body(updatedDepartment);
    }

    @DeleteMapping("/departments/{departmentCode}")
    public ResponseEntity<Map<String , Boolean>> deleteDepartment(@PathVariable("departmentCode") String departmentCode)throws EntityNotFoundException,InvalidInputException
    {
        if(departmentCode == null || departmentCode.trim().equals(""))
        {
            throw new InvalidInputException("departmentCode is not a valid");
        }

        return ResponseEntity.ok().body(departmentService.deleteDepartment(departmentCode));
    }
}
