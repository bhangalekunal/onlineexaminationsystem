package com.codemaster.entity.classfielddetails;

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
@Table(name = "FIELDDETAILS")
public class FieldDetails {
    @Id
    @NotNull(message = "fieldDetailsId cannot be null")
    @Column(name = "FIELDDETAILSID",length = 50,unique = true,nullable = false)
    private String fieldDetailsId;

    @NotNull(message = "className cannot be null")
    @Column(name = "CLASSNAME",length = 50,nullable = false)
    private String className;

    @NotNull(message = "displayFieldName cannot be null")
    @Column(name = "DISPLAYFIELDNAME",length = 50,nullable = false)
    private String displayFieldName;

    @NotNull(message = "actualFieldName cannot be null")
    @Column(name = "ACTUALFIELDNAME",length = 50,nullable = false)
    private String actualFieldName;

    @NotNull(message = "datatype cannot be null")
    @Column(name = "DATATYPE",length = 20,nullable = false)
    private String datatype;

    @Column(name = "DESCRIPTION",length = 50)
    private String decription;

    @NotNull(message = "status cannot be null")
    @Column(name = "status",length = 50,nullable = false)
    private String status;
}
