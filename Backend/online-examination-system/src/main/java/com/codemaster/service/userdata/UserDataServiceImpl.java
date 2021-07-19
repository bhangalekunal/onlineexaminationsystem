package com.codemaster.service.userdata;

import com.codemaster.entity.role.Role;
import com.codemaster.entity.userdata.UserData;
import com.codemaster.entity.userprinciple.UserPrincipal;
import com.codemaster.exceptionhandler.domain.*;
import com.codemaster.repository.userdata.UserDataRepository;
import com.codemaster.service.emailservice.EmailService;
import com.codemaster.service.loginattempt.LoginAttemptService;
import com.codemaster.service.role.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.TimeZone;

import static com.codemaster.constant.FileConstant.*;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.apache.commons.lang3.StringUtils.*;
import static com.codemaster.constant.UserImplConstant.*;


@Service
@Transactional
@Qualifier("userDetailsService")
public class UserDataServiceImpl implements UserDataService, UserDetailsService {

    private Logger LOGGER = LoggerFactory.getLogger(getClass());
    private UserDataRepository userDataRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private LoginAttemptService loginAttemptService;
    private EmailService emailService;
    private RoleService roleService;
    @Autowired
    public UserDataServiceImpl(UserDataRepository userDataRepository, BCryptPasswordEncoder passwordEncoder, LoginAttemptService loginAttemptService, EmailService emailService,
                               RoleService roleService) {
        this.userDataRepository = userDataRepository;
        this.passwordEncoder = passwordEncoder;
        this.loginAttemptService = loginAttemptService;
        this.emailService = emailService;
        this.roleService = roleService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserData userData = userDataRepository.findByUserName(username);
        if (userData == null) {
            LOGGER.error(NO_USER_FOUND_BY_USERNAME + username);
            throw new UsernameNotFoundException(NO_USER_FOUND_BY_USERNAME + username);
        } else {
            validateLoginAttempt(userData);
            UserPrincipal userPrincipal = new UserPrincipal(userData);
            LOGGER.info(FOUND_USER_BY_USERNAME + username);
            return userPrincipal;
        }
    }

    private void validateLoginAttempt(UserData userData) {
        if (userData.isNotLocked())
        {
            if(loginAttemptService.hasExceededMaxAttempts(userData.getUserName()))
            {
                userData.setNotLocked(false);
            }
            else
            {
                userData.setNotLocked(true);
            }
        }
        else
        {
            loginAttemptService.evictUserFromLoginAttemptCache(userData.getUserName());
        }
    }




    @Override
    public UserData register(UserData userData) throws EmailExistException, UsernameExistException, MessagingException, NoActiveRoleFoundException, UserNotFoundException {
        validateNewUsernameAndEmail(EMPTY,userData.getUserName(),userData.getEmail());

        String origionalPassword = userData.getPassword();
        String encodedPassword = encodePassword(origionalPassword);
        userData.setPassword(encodedPassword);
        userData.setFistTime(true);
        userData.setActive(true);
        userData.setNotLocked(true);
        userData.setProfileImageUrl(getTemporaryProfileImageUrl(userData.getUserName()));

        //System.out.println("USERDATA:---->"+userData.toString());
        Role role = roleService.getRoleByRoleId(userData.getRole().getRoleId());
        userData.setRole(role);
        emailService.sendNewPasswordEmail(userData.getFirstName(), origionalPassword, userData.getEmail());
        LOGGER.info("New user password: " + userData.getPassword());
        return userDataRepository.save(userData);
    }


    @Override
    public UserData addNewUserData(String userName, String firstName, String middleName, String lastName, String password, String email, String role, boolean isFistTime, TimeZone timezone, boolean isActive, boolean isNotLocked, MultipartFile profileImage) throws UserNotFoundException, EmailExistException, UsernameExistException, NoActiveRoleFoundException, IOException {
        validateNewUsernameAndEmail(EMPTY,userName,email);
        UserData userData = new UserData();
        userData.setUserName(userName);
        userData.setFirstName(firstName);
        userData.setMiddleName(middleName);
        userData.setLastName(lastName);
        userData.setPassword(encodePassword(password));
        userData.setEmail(email);
        Role roleByRoleId = roleService.getRoleByRoleId(role);
        userData.setRole(roleByRoleId);
        userData.setFistTime(isFistTime);
        userData.setTimezone(timezone);
        userData.setActive(isActive);
        userData.setNotLocked(isNotLocked);
        userData.setProfileImageUrl(getTemporaryProfileImageUrl(userName));
        userDataRepository.save(userData);
        saveProfileImage(userData, profileImage);
        return userData;
    }

    @Override
    public UserData updateUserData(String currentUserName, String newUserName, String newFirstName, String newMiddleName, String newLastName, String newEmail, String newRole, boolean isFistTime, TimeZone timezone, boolean isActive, boolean isNotLocked, MultipartFile profileImage) throws UserNotFoundException, EmailExistException, UsernameExistException, NoActiveRoleFoundException, IOException {
        UserData currentUserData = validateNewUsernameAndEmail(EMPTY,newUserName,newEmail);
        currentUserData.setUserName(newUserName);
        currentUserData.setFirstName(newFirstName);
        currentUserData.setMiddleName(newMiddleName);
        currentUserData.setLastName(newLastName);
        currentUserData.setEmail(newEmail);
        Role roleByRoleId = roleService.getRoleByRoleId(newRole);
        currentUserData.setRole(roleByRoleId);
        currentUserData.setFistTime(isFistTime);
        currentUserData.setTimezone(timezone);
        currentUserData.setActive(isActive);
        currentUserData.setNotLocked(isNotLocked);
        userDataRepository.save(currentUserData);
        saveProfileImage(currentUserData, profileImage);
        return currentUserData;
    }

    @Override
    public void deleteUserData(String userName) {
        userDataRepository.deleteById(userName);
    }

    @Override
    public void resetPassword(String email, String newPassword) throws EmailNotFoundException, MessagingException {
        UserData userData = userDataRepository.findByEmail(email);
        if(userData == null){
            throw new EmailNotFoundException(NO_USER_FOUND_BY_EMAIL + email);
        }
        userData.setPassword(encodePassword(newPassword));
        userDataRepository.save(userData);
        emailService.sendNewPasswordEmail(userData.getFirstName(),newPassword,userData.getEmail());
    }

    @Override
    public List<UserData> getAllUserData() {
        return userDataRepository.findAll();
    }

    @Override
    public UserData findUserDataByUsername(String username) {
        return userDataRepository.findByUserName(username);
    }

    @Override
    public UserData findUserDataByEmail(String email) {
        return userDataRepository.findByEmail(email);
    }


    @Override
    public UserData updateProfileImage(String userName, MultipartFile profileImage) throws UserNotFoundException, EmailExistException, UsernameExistException, IOException {
        UserData userData = validateNewUsernameAndEmail(userName,null,null);
        saveProfileImage(userData,profileImage);
        return userData;
    }

    private String getTemporaryProfileImageUrl(String userName) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(DEFAULT_USER_IMAGE_PATH + userName).toUriString();
    }

    private void saveProfileImage(UserData userData, MultipartFile profileImage) throws IOException {
        if (profileImage != null){
            Path userFolder = Paths.get(USER_FOLDER + userData.getUserName()).toAbsolutePath().normalize();
            if(!Files.exists(userFolder)){
                Files.createDirectories(userFolder);
                LOGGER.info(DIRECTORY_CREATED + userFolder);
            }
            Files.deleteIfExists(Paths.get(userFolder + userData.getUserName() + DOT + JPG_EXTENSION));
            Files.copy(profileImage.getInputStream(),userFolder.resolve(userData.getUserName() + DOT + JPG_EXTENSION), REPLACE_EXISTING);
            userData.setProfileImageUrl(setProfileImageUrl(userData.getUserName()));
            userDataRepository.save(userData);
            LOGGER.info(FILE_SAVED_IN_FILE_SYSTEM + profileImage.getOriginalFilename());
        }

    }

    private String setProfileImageUrl(String userName) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(USER_IMAGE_PATH + userName + DOT + JPG_EXTENSION).toUriString();
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    private UserData validateNewUsernameAndEmail(String currentUsername, String newUsername, String newEmail) throws UserNotFoundException, UsernameExistException, EmailExistException {
        UserData userDataByNewUsername = findUserDataByUsername(newUsername);
        UserData userDataByNewEmail = findUserDataByEmail(newEmail);
        if(StringUtils.isNotBlank(StringUtils.EMPTY)) {
            UserData currentUserData = findUserDataByUsername(StringUtils.EMPTY);
            if(currentUserData == null) {
                throw new UserNotFoundException(NO_USER_FOUND_BY_USERNAME + StringUtils.EMPTY);
            }
            if(userDataByNewUsername != null && !currentUserData.getUserName().equals(userDataByNewUsername.getUserName())) {
                throw new UsernameExistException(USERNAME_ALREADY_EXISTS);
            }
            if(userDataByNewEmail != null && !currentUserData.getUserName().equals(userDataByNewEmail.getUserName())) {
                throw new EmailExistException(EMAIL_ALREADY_EXISTS);
            }
            return currentUserData;
        } else {
            if(userDataByNewUsername != null) {
                throw new UsernameExistException(USERNAME_ALREADY_EXISTS);
            }
            if(userDataByNewEmail != null) {
                throw new EmailExistException(EMAIL_ALREADY_EXISTS);
            }
            return null;
        }
    }

}
