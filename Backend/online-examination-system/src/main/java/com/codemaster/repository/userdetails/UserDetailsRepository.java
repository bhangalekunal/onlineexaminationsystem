package com.codemaster.repository.userdetails;

import com.codemaster.entity.userdetails.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserDetailsRepository extends JpaRepository<UserDetails, String> {
    @Query("SELECT u FROM UserDetails u WHERE u.userName = ?1 and u.active = 1")
    List<UserDetails> findByUserName(@Param("userName") String userName);
}
