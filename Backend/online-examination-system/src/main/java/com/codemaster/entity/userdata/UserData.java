package com.codemaster.entity.userdata;

import com.codemaster.entity.role.Role;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.TimeZone;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USERDATA")
public class UserData implements Serializable {
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
    @Column(name = "ISFIRSTTIME",nullable = false)
    @JsonProperty
    private boolean isFistTime;

    @NotNull(message = "timezone cannot be null")
    @Column(name = "TIMEZONE",nullable = false)
    private TimeZone timezone;

    @NotNull(message = "isActive cannot be null")
    @Column(name = "ISACTIVE",nullable = false)
    @JsonProperty
    private boolean isActive;

    @NotNull(message = "isNotLocked cannot be null")
    @Column(name = "ISNOTLOCKED",nullable = false)
    @JsonProperty
    private boolean isNotLocked;

    @Column(name = "PROFILEIMAGEURL")
    private String profileImageUrl;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ROLEID", nullable = false)
    @JsonBackReference
    private Role role;


}
