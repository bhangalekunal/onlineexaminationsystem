package com.codemaster.nonpersistanceentity;

import com.codemaster.entity.userdetails.UserDetails;
import lombok.*;

import javax.persistence.Entity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AuthResponse {
    private String status;
    private String message;
    private String sessionToken;
    private UserDetails userDetails;
    private String authType;
}
