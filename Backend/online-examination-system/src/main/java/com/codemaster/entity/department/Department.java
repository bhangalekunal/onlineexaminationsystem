package com.codemaster.entity.department;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "DEPARTMENT")
public class Department {

    @Id
    @NotNull(message = "departmentCode cannot be null")
    @Column(name = "DEPARTMENTCODE",length = 50,unique = true,nullable = false)
    private String departmentCode;

    @Column(name = "DEPARTMENTNAME",length = 100)
    private String departmentName;

    @Column(name = "DESCRIPTION",length = 50)
    private String decription;

    @NotNull(message = "status cannot be null")
    @Column(name = "STATUS",length = 50,nullable = false)
    private String status;
}
