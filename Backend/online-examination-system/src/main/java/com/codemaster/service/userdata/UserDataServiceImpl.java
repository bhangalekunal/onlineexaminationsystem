package com.codemaster.service.userdata;

import com.codemaster.entity.role.Role;
import com.codemaster.entity.userdata.UserData;
import com.codemaster.entity.userprinciple.UserPrincipal;
import com.codemaster.exceptionhandler.domain.EmailExistException;
import com.codemaster.exceptionhandler.domain.NoActiveRoleFoundException;
import com.codemaster.exceptionhandler.domain.UsernameExistException;
import com.codemaster.repository.userdata.UserDataRepository;
import com.codemaster.service.emailservice.EmailService;
import com.codemaster.service.loginattempt.LoginAttemptService;
import com.codemaster.service.role.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

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

    /*
    @Override
    public UserData register(UserData userData) throws EmailExistException, UsernameExistException, MessagingException {
        validateNewUsernameAndEmail(userData);

        String origionalPassword = userData.getPassword();
        String encodedPassword = encodePassword(origionalPassword);
        userData.setPassword(encodedPassword);
        userData.setFistTime(true);
        userData.setActive(true);
        userData.setNotLocked(true);
        userData.setRole(ROLE_USER.name());
        userData.setAuthorities(ROLE_USER.getAuthorities());
        userData.setProfileImageUrl(getTemporaryProfileImageUrl());

        userDataRepository.save(userData);
        emailService.sendNewPasswordEmail(userData.getFirstName(), origionalPassword, userData.getEmail());
        LOGGER.info("New user password: " + userData.getPassword());
       return userDataRepository.save(userData);
    }
    */


    @Override
    public UserData register(UserData userData) throws EmailExistException, UsernameExistException, MessagingException, NoActiveRoleFoundException {
        validateNewUsernameAndEmail(userData);

        String origionalPassword = userData.getPassword();
        String encodedPassword = encodePassword(origionalPassword);
        userData.setPassword(encodedPassword);
        userData.setFistTime(true);
        userData.setActive(true);
        userData.setNotLocked(true);
        userData.setProfileImageUrl(getTemporaryProfileImageUrl());

        Role role = roleService.getRoleByRoleId("ADMIN");
        userData.setRole(role);
        //System.out.println("USERDATA:---->"+userData.toString());
        List<UserData> userDataList = new ArrayList<>();
        userDataList.add(userData);
        //role.setUserDataSet(userDataList);
        //roleService.addRole(role);
        //userDataRepository.save(userData);
        emailService.sendNewPasswordEmail(userData.getFirstName(), origionalPassword, userData.getEmail());
        LOGGER.info("New user password: " + userData.getPassword());
        return userDataRepository.save(userData);
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
    public UserData fincUserDataByEmail(String email) {
        return userDataRepository.findByEmail(email);
    }

    private String getTemporaryProfileImageUrl() {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(DEFAULT_USER_IMAGE_PATH).toUriString();
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    private void validateNewUsernameAndEmail(UserData userData) throws UsernameExistException, EmailExistException {
        UserData userDataByUsername = findUserDataByUsername(userData.getUserName());
        if(userDataByUsername != null)
        {
            throw new UsernameExistException(USERNAME_ALREADY_EXISTS);
        }

        UserData userDataByEmail = fincUserDataByEmail(userData.getEmail());
        if(userDataByEmail != null)
        {
            throw new EmailExistException(EMAIL_ALREADY_EXISTS);
        }
    }
}
