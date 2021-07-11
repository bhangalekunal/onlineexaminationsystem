package com.codemaster.repository.userdata;

import com.codemaster.entity.userdata.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserDataRepository extends JpaRepository<UserData, String> {
    @Query("SELECT u FROM UserData u WHERE u.userName = ?1 and u.isActive = 1")
    UserData findByUserName(@Param("userName") String userName);



    @Query("SELECT u FROM UserData u WHERE u.email = ?1 and u.isActive = 1")
    UserData findByEmail(@Param("email") String email);


}
