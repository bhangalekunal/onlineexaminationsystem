package com.codemaster.entity.role;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ROLE")
public class Role {
    @Id
    @NotNull(message = "roleId cannot be null")
    @Column(name = "ROLEID",length = 50,unique = true,nullable = false)
    private String roleId;

    @NotNull(message = "roleName cannot be null")
    @Column(name = "ROLENAME",length = 50,nullable = false)
    private String roleName;

    @NotNull(message = "status cannot be null")
    @Column(name = "STATUS",length = 50,nullable = false)
    private String status;



}
