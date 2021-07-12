package com.codemaster.repository.subject;

import com.codemaster.entity.subject.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject,String> {
    @Query("SELECT s FROM Subject s WHERE s.subjectCode = ?1 and s.status='ACTIVE'")
    List<Subject> findBySubjectCode(@Param("subjectCode") String subjectCode);
}
