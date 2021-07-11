package com.codemaster.listners;

import com.codemaster.entity.userdata.UserData;
import com.codemaster.service.loginattempt.LoginAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessListner {
    private LoginAttemptService loginAttemptService;

    @Autowired
    public AuthenticationSuccessListner(LoginAttemptService loginAttemptService) {
        this.loginAttemptService = loginAttemptService;
    }

    @EventListener
    public void onAuthenticationSuccess(AuthenticationSuccessEvent event)
    {
        Object principle = event.getAuthentication().getPrincipal();
        if(principle instanceof UserData)
        {
            UserData userData = (UserData) event.getAuthentication().getPrincipal();
            loginAttemptService.evictUserFromLoginAttemptCache(userData.getUserName());
        }
    }
}
