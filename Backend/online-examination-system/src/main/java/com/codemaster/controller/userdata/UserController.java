package com.codemaster.controller.userdata;

import com.codemaster.entity.nonpersistance.AuthRequest;
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
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.validation.Valid;

import java.io.IOException;
import java.util.TimeZone;

import static com.codemaster.constant.SecurityConstant.JWT_TOKEN_HEADER;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/user")
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
    public ResponseEntity<UserData> login(@Valid @RequestBody AuthRequest authRequest) {
        authenticate(authRequest.getUserName(), authRequest.getPassword());
        UserData loginUserData = userDataService.findUserDataByUsername(authRequest.getUserName());
        UserPrincipal userPrincipal = new UserPrincipal(loginUserData);
        HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
        return new ResponseEntity<>(loginUserData, jwtHeader, OK);
    }

    @PostMapping("/register")
    public ResponseEntity<UserData> register(@RequestBody UserData userData) throws UserNotFoundException, UsernameExistException, EmailExistException, MessagingException, NoActiveRoleFoundException {
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

    @PostMapping("/add")
    public ResponseEntity<UserData> addUserData(@RequestParam("userName") String userName,
                                                @RequestParam("firstName") String firstName,
                                                @RequestParam("middleName") String middleName,
                                                @RequestParam("lastName") String lastName,
                                                @RequestParam("password") String password,
                                                @RequestParam("email") String email,
                                                @RequestParam("role") String role,
                                                @RequestParam("isFistTime") String isFistTime,
                                                @RequestParam("timezone") TimeZone timeZone,
                                                @RequestParam("isActive") String isActive,
                                                @RequestParam("isNotLocked") String isNotLocked,
                                                @RequestParam(value = "profileImage",required = false) MultipartFile profileImage) throws UserNotFoundException, NoActiveRoleFoundException, EmailExistException, IOException, UsernameExistException {
        UserData newUserData = userDataService.addNewUserData(userName, firstName, middleName, lastName, password, email, role, Boolean.parseBoolean(isFistTime), timeZone, Boolean.parseBoolean(isActive), Boolean.parseBoolean(isNotLocked), profileImage);
        return new ResponseEntity<>(newUserData,OK);

    }

    @PostMapping("/update")
    public ResponseEntity<UserData> updateUserData(@RequestParam("currentUserName") String currentUserName,
                                                @RequestParam("userName") String userName,
                                                @RequestParam("firstName") String firstName,
                                                @RequestParam("middleName") String middleName,
                                                @RequestParam("lastName") String lastName,
                                                @RequestParam("email") String email,
                                                @RequestParam("role") String role,
                                                @RequestParam("isFistTime") String isFistTime,
                                                @RequestParam("timezone") TimeZone timeZone,
                                                @RequestParam("isActive") String isActive,
                                                @RequestParam("isNotLocked") String isNotLocked,
                                                @RequestParam(value = "profileImage",required = false) MultipartFile profileImage) throws UserNotFoundException, NoActiveRoleFoundException, EmailExistException, IOException, UsernameExistException {
        UserData updatedUserData = userDataService.addNewUserData(currentUserName, userName, firstName, middleName, lastName, email, role, Boolean.parseBoolean(isFistTime), timeZone, Boolean.parseBoolean(isActive), Boolean.parseBoolean(isNotLocked), profileImage);
        return new ResponseEntity<>(updatedUserData,OK);
    }

    @GetMapping("/find/{userName}")
    public ResponseEntity<UserData> getUserData(@PathVariable("userName") String userName){
        UserData userData = userDataService.findUserDataByUsername(userName);
        return new ResponseEntity<>(userData,OK);
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
