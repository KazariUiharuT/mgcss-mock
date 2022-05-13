package com.acme.bnb.model;

import com.acme.bnb.model.datatype.CreditCard;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Entity
@DiscriminatorValue("lessor")
public class Lessor extends Actor {
    
    @JsonIgnore
    @Embedded
    private CreditCard creditCard;
    
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToOne(optional = false, cascade = CascadeType.REMOVE)
    private Commentable commentable;
    
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "propietary", cascade = CascadeType.REMOVE)
    private Collection<Property> properties;
}
