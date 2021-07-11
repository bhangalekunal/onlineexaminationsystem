package com.codemaster.entity.nonpersistance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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