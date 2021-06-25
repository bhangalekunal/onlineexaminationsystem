package com.codemaster.entity.userdetails;

import com.codemaster.entity.role.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USERDETAILS")
public class UserDetails {

    @Id
    @NotNull(message = "userName cannot be null")
    @Column(name = "USERNAME",length = 50,unique = true,nullable = false)
    private String userName;

    @NotNull(message = "firstName cannot be null")
    @Column(name = "FIRSTNAME",length = 50,nullable = false)
    private String firstName;

    @Column(name = "MIDDLENAME",length = 50)
    private String middleName;

    @NotNull(message = "lastName cannot be null")
    @Column(name = "LASTNAME",length = 50,nullable = false)
    private String lastName;

    @NotNull(message = "password cannot be null")
    @Column(name = "PASSWORD",length = 200,nullable = false)
    private String password;

    @NotNull(message = "email cannot be null")
    @Email(message = "email should be valid")
    @Column(name = "EMAIL",length = 50,nullable = false)
    private String email;

    @NotNull(message = "isFistTime cannot be null")
    @Column(name = "ISFIRSTTIME",columnDefinition = "NUMBER(1,0)",nullable = false)
    private boolean isFistTime;

    @NotNull(message = "timezone cannot be null")
    @Column(name = "TIMEZONE",nullable = false)
    private TimeZone timezone;

    @NotNull(message = "active cannot be null")
    @Column(name = "ACTIVE",columnDefinition = "NUMBER(1,0)",nullable = false)
    private boolean active;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "USERROLEASSIGNMENT",
            joinColumns = @JoinColumn(name = "USERNAME"),
            inverseJoinColumns = @JoinColumn(name = "ROLEID")
    )
    private Set<Role> roles = new HashSet<>();
}
