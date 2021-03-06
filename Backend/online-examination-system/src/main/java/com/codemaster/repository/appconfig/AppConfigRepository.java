package com.codemaster.repository.appconfig;

import com.codemaster.entity.appconfig.AppConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AppConfigRepository extends JpaRepository<AppConfig, String> {
    @Query("SELECT a FROM AppConfig a WHERE a.keyName = ?1 and a.status = 'ACTIVE'")
    AppConfig findByKeyName(@Param("keyName") String keyName);

}
