package com.codemaster.repository.fielddetails;

import com.codemaster.entity.classfielddetails.FieldDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FieldDetailsRepository extends JpaRepository<FieldDetails, String> {
    @Query("SELECT f FROM FieldDetails f WHERE f.className = ?1 and f.status = 'ENABLED'")
    List<FieldDetails> findByClassName(@Param("className") String className);
}
