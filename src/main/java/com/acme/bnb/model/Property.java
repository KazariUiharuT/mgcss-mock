package com.acme.bnb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Digits;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Entity
public class Property extends DomainEntity {

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, columnDefinition = "text")
    private String description;

    @Digits(integer = 9, fraction = 2)
    @Column(nullable = false)
    private double rate;

    @Column(nullable = false)
    private String address;
    
    @CreationTimestamp
    @Column(nullable = false) //updatable = false
    private Date date;

    @Column(nullable = false, columnDefinition = "integer default 0")
    private int nRequests;
    
    @Column(nullable = false, columnDefinition = "integer default 0")
    private int nAudits;

    @ManyToOne(optional = false)
    private Lessor propietary;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "property", cascade = CascadeType.REMOVE)
    private Collection<PropertyPicture> pictures;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "property", cascade = CascadeType.REMOVE)
    private Collection<PropertyAttributeValue> attributes;

    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "property", cascade = CascadeType.REMOVE)
    private Collection<Request> requests;

    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "property", cascade = CascadeType.REMOVE)
    private Collection<Audit> audits;
}
