package com.acme.bnb.model;

import com.acme.bnb.model.datatype.Phone;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "actor_type")
public abstract class Actor extends DomainEntity {

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String surname;

    @Email
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Embedded
    @Column(nullable = false)
    private Phone phone;

    private String picture;

    @JsonIgnore
    @Column(nullable = false, length = 64)
    private String pwd;
    
    @Transient
    @JsonSerialize
    private double stars;
    
    @Transient
    @JsonSerialize
    private String type;

    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "actor", cascade = CascadeType.REMOVE)
    private Collection<SocialIdentity> socialIdentities;
    
    @JsonIgnore
    @OneToMany(mappedBy = "author")
    private Collection<Comment> comments;
}
