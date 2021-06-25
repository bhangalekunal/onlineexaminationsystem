package com.codemaster.entity.subject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SUBJECT")
public class Subject {

    @Id
    @NotNull(message = "subjectCode cannot be null")
    @Column(name = "SUBJECTCODE",length = 50,unique = true,nullable = false)
    private String subjectCode;

    @Column(name = "SUBJECTNAME",length = 100)
    private String subjectName;

    @Column(name = "DESCRIPTION",length = 50)
    private String decription;

    @NotNull(message = "status cannot be null")
    @Column(name = "STATUS",length = 50,nullable = false)
    private String status;
}
