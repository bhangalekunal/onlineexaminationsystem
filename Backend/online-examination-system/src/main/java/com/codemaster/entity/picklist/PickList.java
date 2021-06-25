package com.codemaster.entity.picklist;


import com.codemaster.entity.picklistoption.PickListOption;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PICKLIST")
public class PickList {

    @Id
    @NotNull(message = "listId cannot be null")
    @Column(name = "LISTID",length = 50,unique = true,nullable = false)
    private String listId;

    @NotNull(message = "listName cannot be null")
    @Column(name = "LISTNAME",length = 50,unique = true)
    private String listName;

    @Column(name = "DESCRIPTION",length = 50 )
    private String description;

    @NotNull(message = "status cannot be null")
    @Column(name = "STATUS",length = 50,nullable = false)
    private String status;

    @OneToMany(mappedBy = "pickList", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<PickListOption> options;


}
