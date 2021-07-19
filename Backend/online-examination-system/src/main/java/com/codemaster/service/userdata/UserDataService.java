package com.codemaster.service.userdata;

import com.codemaster.entity.userdata.UserData;
import com.codemaster.exceptionhandler.domain.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;
import java.util.TimeZone;

public interface UserDataService {
    UserData register(UserData userData) throws EmailExistException, UsernameExistException, MessagingException, NoActiveRoleFoundException, UserNotFoundException;

    List<UserData> getAllUserData();

    UserData findUserDataByUsername(String username);

    UserData findUserDataByEmail(String email);

    UserData addNewUserData(String userName, String firstName, String middleName, String lastName, String password, String email, String role,
                        boolean isFistTime, TimeZone timezone,boolean isActive, boolean isNotLocked,
                        MultipartFile profileImage) throws UserNotFoundException, EmailExistException, UsernameExistException, NoActiveRoleFoundException, IOException;

    UserData updateUserData(String currentUserName, String newUserName, String newFirstName, String newMiddleName, String newLastName, String newEmail, String newRole,
                            boolean isFistTime, TimeZone timezone, boolean isActive, boolean isNotLocked,
                            MultipartFile profileImage) throws UserNotFoundException, EmailExistException, UsernameExistException, NoActiveRoleFoundException, IOException;

    void deleteUserData(String userName);

    void resetPassword(String email, String newPassword) throws EmailNotFoundException, MessagingException;

    UserData updateProfileImage(String userName, MultipartFile profileImage) throws UserNotFoundException, EmailExistException, UsernameExistException, IOException;
}
