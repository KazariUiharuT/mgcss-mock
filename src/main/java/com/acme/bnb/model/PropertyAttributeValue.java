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
public class PropertyAttributeValue extends DomainEntity {
    
    @Column(nullable = false, length = 100)
    private String value;
    
    @ManyToOne(optional = false)
    private PropertyAttribute attribute;
    
    @JsonIgnore
    @ManyToOne(optional = false)
    private Property property;
}
