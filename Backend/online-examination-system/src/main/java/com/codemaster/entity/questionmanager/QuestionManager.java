package com.codemaster.entity.questionmanager;

import lombok.*;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "QUESTIONMANAGER")
public class QuestionManager {
    @Id
    @NotNull(message = "quesionId can not be null")
    @Column(name = "QUESTIONID", length = 36,unique = true, nullable = false)
    private String questionId;

    @NotNull(message = "question cannot be null")
    @Column(name = "QUESTION",length = 300, nullable = false)
    private String question;

    @NotNull(message = "option1 can not be null")
    @Column(name = "OPTION1",length = 300, nullable = false)
    private String option1;

    @NotNull(message = "option2 can not be null")
    @Column(name = "OPTION2",length = 300, nullable = false)
    private String option2;

    @NotNull(message = "option3 can not be null")
    @Column(name = "OPTION3",length = 300, nullable = false)
    private String option3;

    @NotNull(message = "option4 can not be null")
    @Column(name = "OPTION4",length = 300, nullable = false)
    private String option4;

    @NotNull(message = "answer can not be null")
    @Column(name = "ANSWER",length = 7, nullable = false)
    private String answer;

    @Column(name = "REASON",length = 300, nullable = true)
    private String reason;

    @NotNull(message = "marks can not be null")
    @Column(name = "MARKS",length = 38, nullable = false)
    private int marks;

    @NotNull(message = "subjectCode can not be null")
    @Column(name = "SUBJECTCODE",length = 50, nullable = false)
    private String subjectCode;

    @NotNull(message = "departmentCode can not be null")
    @Column(name = "DEPARTMENTCODE",length = 50, nullable = false)
    private String departmentCode;

    @NotNull(message = "status can not be null")
    @Column(name = "STATUS",length = 30, nullable = false)
    private String status;
}
