package com.codemaster.controller.userdata;

import com.codemaster.entity.userdata.UserData;
import com.codemaster.entity.userprinciple.UserPrincipal;
import com.codemaster.exceptionhandler.domain.*;
import com.codemaster.service.userdata.UserDataService;
import com.codemaster.utility.JWTTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

import static com.codemaster.constant.SecurityConstant.JWT_TOKEN_HEADER;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private AuthenticationManager authenticationManager;
    private UserDataService userDataService;
    private JWTTokenProvider jwtTokenProvider;

    @Autowired
    public UserController(AuthenticationManager authenticationManager, UserDataService userDataService, JWTTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userDataService = userDataService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<UserData> login(@RequestBody UserData userData) {
        authenticate(userData.getUserName(), userData.getPassword());
        UserData loginUserData = userDataService.findUserDataByUsername(userData.getUserName());
        UserPrincipal userPrincipal = new UserPrincipal(loginUserData);
        HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
        return new ResponseEntity<>(loginUserData, jwtHeader, OK);
    }

    @PostMapping("/register")
    public ResponseEntity<UserData> register(@RequestBody UserData userData) throws UserNotFoundException, UsernameExistException, EmailExistException, MessagingException {
        UserData newUserData = userDataService.register(userData);
        return new ResponseEntity<>(newUserData, OK);
    }

    @GetMapping("/hello")
    @PreAuthorize("hasAnyAuthority('user:read')")
    public ResponseEntity<String> sayHello()
    {
        return new ResponseEntity<>("Hello World",OK);
    }

    @GetMapping("/test")
    @PreAuthorize("hasAnyAuthority('user:update')")
    public ResponseEntity<String> testflow()
    {
        return new ResponseEntity<>("Hello Test",OK);
    }

    @GetMapping("/test1")
    @PreAuthorize("hasAnyAuthority('user:delete')")
    public ResponseEntity<String> testflow1()
    {
        return new ResponseEntity<>("Hello Test1",OK);
    }

    private HttpHeaders getJwtHeader(UserPrincipal user) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER, jwtTokenProvider.generateJwtToken(user));
        return headers;
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}
