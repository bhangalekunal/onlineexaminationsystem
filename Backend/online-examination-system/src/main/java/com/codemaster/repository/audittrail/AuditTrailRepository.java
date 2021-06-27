package com.codemaster.repository.audittrail;

import com.codemaster.entity.audittrail.AuditTrail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuditTrailRepository extends JpaRepository<AuditTrail, String> {
    @Query("SELECT a FROM AuditTrail a WHERE a.auditId = ?1")
    List<AuditTrail> findByAuditId(@Param("auditId") String auditId);
}
