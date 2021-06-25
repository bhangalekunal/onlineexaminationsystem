package com.codemaster.nonpersistanceentity;

import lombok.*;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    @NotNull(message = "userName cannot be null")
    private String userName;

    @NotNull(message = "password cannot be null")
    private String password;
}