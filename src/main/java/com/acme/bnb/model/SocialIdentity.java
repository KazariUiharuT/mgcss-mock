package com.acme.bnb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class SocialIdentity extends DomainEntity {
    @Column(nullable = false, length = 100)
    private String nick;
    
    @URL
    @Column(nullable = false)
    private String url;
    
    @Column(nullable = false, length = 100)
    private String socialNetwork;
    
    @JsonIgnore
    @ManyToOne(optional = false)
    private Actor actor;
}
