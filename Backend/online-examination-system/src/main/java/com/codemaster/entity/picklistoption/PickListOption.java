package com.codemaster.entity.picklistoption;

import com.codemaster.entity.picklist.PickList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PICKLISTOPTION")
public class PickListOption {

    @Id
    @NotNull(message = "listOptionId cannot be null")
    @Column(name = "LISTOPTIONID",length = 50,unique = true,nullable = false)
    private String listOptionId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "LISTID", nullable = false)
    private PickList pickList;

    @Column(name = "OPTIONNAME",length = 50)
    private String optionName;

    @NotNull(message = "optionValue cannot be null")
    @Column(name = "OPTIONVALUE",length = 50,nullable = false)
    private String optionValue;

    @NotNull(message = "status cannot be null")
    @Column(name = "STATUS",length = 50,nullable = false)
    private String status;
}
