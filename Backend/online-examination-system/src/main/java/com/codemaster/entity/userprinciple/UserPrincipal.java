package com.codemaster.entity.userprinciple;

import com.codemaster.entity.role.Role;
import com.codemaster.entity.userdata.UserData;
import com.codemaster.service.privilige.PriviligeService;
import com.codemaster.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserPrincipal implements UserDetails {
    private UserData userData;



    public UserPrincipal(UserData userData) {
        this.userData = userData;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<String> priviliges = this.userData.getRole().getPrivileges().stream().map(privilege ->
                String.format("%s:%s",privilege.getResourceName(),privilege.getOperation())).collect(Collectors.toList());
        return priviliges.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

    }

    @Override
    public String getPassword() {
        return this.userData.getPassword();
    }

    @Override
    public String getUsername() {
        return this.userData.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.userData.isNotLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.userData.isActive();
    }
}
