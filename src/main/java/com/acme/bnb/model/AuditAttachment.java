package com.acme.bnb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class AuditAttachment extends DomainEntity {
    
    @Column(nullable = false)
    private String value;
    
    @JsonIgnore
    @ManyToOne(optional = false)
    private Audit audit;
}
