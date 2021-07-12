package com.codemaster.entity.role;


import com.codemaster.entity.privilege.Privilege;
import com.codemaster.entity.userdata.UserData;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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


    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<UserData> userDataSet;

    @ManyToMany
    @JoinTable(
            name = "ROLEPRIVILIGEASSIGNMENT",
            joinColumns = @JoinColumn(
                    name = "ROLEID", referencedColumnName = "ROLEID"),
            inverseJoinColumns = @JoinColumn(
                    name = "PRIVILEGEID", referencedColumnName = "PRIVILEGEID"))
    private List<Privilege> privileges;

}
