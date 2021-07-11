package com.codemaster.service.userdata;

import com.codemaster.entity.userdata.UserData;
import com.codemaster.exceptionhandler.domain.EmailExistException;
import com.codemaster.exceptionhandler.domain.UsernameExistException;

import javax.mail.MessagingException;
import java.util.List;

public interface UserDataService {
    UserData register(UserData userData) throws EmailExistException, UsernameExistException, MessagingException;

    List<UserData> getAllUserData();

    UserData findUserDataByUsername(String username);

    UserData fincUserDataByEmail(String email);
}
