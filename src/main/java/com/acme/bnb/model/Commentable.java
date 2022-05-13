package com.acme.bnb.model;

import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Entity
public class Commentable extends DomainEntity {

    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "target", cascade = CascadeType.REMOVE)
    private Collection<Comment> comments;
    
    @OneToOne(mappedBy = "commentable", optional = true)
    private Lessor lessor;
    
    @OneToOne(mappedBy = "commentable", optional = true)
    private Tenant tenant;
}
