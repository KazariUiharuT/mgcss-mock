package com.acme.bnb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@NoArgsConstructor
@Data
@Entity
public class PropertyAttribute extends DomainEntity {

    public PropertyAttribute(String name, boolean sysDefault) {
        this.name = name;
        this.sysDefault = sysDefault;
    }
    
    @Column(nullable = false, length = 100, unique = true)
    private String name;
    
    @Column(nullable = false)
    private boolean sysDefault;

    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "attribute", cascade = CascadeType.REMOVE)
    private Collection<PropertyAttributeValue> values;
}
