package com.codemaster.entity.privilege;


import com.codemaster.entity.role.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PRIVILEGE")
public class Privilege {
    @Id
    @NotNull(message = "privilegeId cannot be null")
    @Column(name = "PRIVILEGEID",length = 50,unique = true,nullable = false)
    private String privilegeId;

    @Column(name = "RESOURCENAME")
    private String resourceName;

    @Column(name = "OPERATION")
    private String operation;

    @ManyToMany(mappedBy = "privileges")
    @JsonIgnoreProperties("privileges")
    private List<Role> roles;

}
