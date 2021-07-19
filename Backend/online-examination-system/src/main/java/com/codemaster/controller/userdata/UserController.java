package com.codemaster.controller.userdata;

import com.codemaster.entity.httpresponse.HttpResponse;
import com.codemaster.entity.nonpersistance.AuthRequest;

import com.codemaster.entity.nonpersistance.ResetPasswordRequest;
import com.codemaster.entity.userdata.UserData;
import com.codemaster.entity.userprinciple.UserPrincipal;
import com.codemaster.exceptionhandler.domain.*;
import com.codemaster.service.userdata.UserDataService;
import com.codemaster.utility.JWTTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import static com.codemaster.constant.FileConstant.*;
import static org.springframework.http.MediaType.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.validation.Valid;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.TimeZone;

import static com.codemaster.constant.SecurityConstant.JWT_TOKEN_HEADER;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/user")
public class UserController {
    public static final String EMAIL_SENT = "An email with new Password was sent to: ";
    public static final String USER_DATA_DELETED_SUCCESSFULLY = "UserData deleted successfully";
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

    @GetMapping("/list")
    public ResponseEntity<List<UserData>> getAllUserData(){
        List<UserData> userDataList = userDataService.getAllUserData();
        return new ResponseEntity<>(userDataList,OK);
    }

    @GetMapping("/resetPassword")
    public ResponseEntity<HttpResponse> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) throws EmailNotFoundException, MessagingException {
        userDataService.resetPassword(resetPasswordRequest.getEmail(),resetPasswordRequest.getPassword());
        return response(OK, EMAIL_SENT + resetPasswordRequest.getEmail());
    }

    @DeleteMapping("/delete/{userName}")
    public ResponseEntity<HttpResponse> deleteUserData(@PathVariable("userName") String userName){
        userDataService.deleteUserData(userName);
        return response(NO_CONTENT, USER_DATA_DELETED_SUCCESSFULLY);
    }

    @PostMapping("/updateProfileImage")
    public ResponseEntity<UserData> updateProfileImage(@RequestParam("userName") String userName, @RequestParam(value = "profileImage",required = false) MultipartFile profileImage) throws UserNotFoundException, EmailExistException, IOException, UsernameExistException {
        UserData userData = userDataService.updateProfileImage(userName,profileImage);
        return new ResponseEntity<>(userData,OK);
    }

    @GetMapping(path = "/image/{userName}/{fileName}", produces = IMAGE_JPEG_VALUE)
    public byte[] getProfileImage(@PathVariable("userName") String userName,@PathVariable("fileName") String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(USER_FOLDER + userName + FORWARD_SLASH + fileName));
    }

    @GetMapping(path = "/image/profile/{userName}", produces = IMAGE_JPEG_VALUE)
    public byte[] getTempProfileImage(@PathVariable("userName") String userName) throws IOException {
        URL url = new URL(TEMP_PROFILE_IMAGE_BASE_URL + userName);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try(InputStream inputStream = url.openStream()){
            int bytesRead;
            byte[] chunk = new byte[1024];
            while ((bytesRead = inputStream.read(chunk)) > 0){
                byteArrayOutputStream.write(chunk, 0, bytesRead);
            }
        }
        return byteArrayOutputStream.toByteArray();
    }

    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        HttpResponse httpResponse = new HttpResponse(httpStatus.value(), httpStatus ,httpStatus.getReasonPhrase().toUpperCase(), message);
        return new ResponseEntity<>(httpResponse,httpStatus);
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
