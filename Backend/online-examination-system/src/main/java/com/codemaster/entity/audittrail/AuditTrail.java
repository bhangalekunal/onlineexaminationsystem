package com.codemaster.entity.audittrail;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "AUDITTRAIL")
public class AuditTrail {
    @Id
    @NotNull(message = "Audit Id cannot be null")
    @Size(min = 1,max = 36,message = "The auditId '${validatedValue}' must be between {min} and {max} characters long")
    @Column(name = "AUDITID",length = 36)
    private int auditId;

    @NotNull(message = "userName cannot be null")
    @Size(min = 2, message = "The userName '${validatedValue}' must be {min} characters long")
    @Column(name = "USERNAME",length = 50)
    private String userName;

    @NotNull(message = "dateTime cannot be null")
    @Column(name = "DATETIME")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dateTime;

    @NotNull(message = "action cannot be null")
    @Size(min = 2, message = "The action '${validatedValue}' must be {min} characters long")
    @Column(name = "ACTION",length = 50)
    private String action;

    @Lob
    @Column(name = "OLDDATA", columnDefinition="BLOB")
    private byte[] oldData;

    @Lob
    @Column(name = "NEWDATA", columnDefinition="BLOB")
    private String newData;

    @NotNull(message = "ipAddress cannot be null")
    @Pattern(regexp = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$")
    @Column(name = "IPADDRESS",length = 50)
    private String ipAddress;

    @NotNull(message = "geoLocation cannot be null")
    @Column(name = "GEOLOCATION",length = 100)
    private String geoLocation;
}
