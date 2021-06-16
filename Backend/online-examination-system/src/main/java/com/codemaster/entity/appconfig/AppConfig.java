package com.codemaster.entity.appconfig;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "APPCONFIG")
public class AppConfig {
    @Id
    @NotNull(message = "keyName can not be Null")
    @Column(name = "KEYNAME",length = 100,unique = true, nullable = false)
    private String keyName;

    @NotNull(message = "value can not be null")
    @Column(name = "VALUE",length = 100,unique = false,nullable = false)
    private String value;

    @NotNull(message = "status can not be null")
    @Column(name = "STATUS",length = 50,unique = false,nullable = false)
    private String status;
}
