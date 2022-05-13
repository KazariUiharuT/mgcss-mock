package com.acme.bnb.model;

import com.acme.bnb.model.datatype.CreditCard;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.validation.constraints.Digits;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

@Data
@Entity
public class Invoice extends DomainEntity {

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Date date;

    @Column()
    private String pdf;

    @Column(nullable = false)
    private String vat;

    @Embedded
    @Column(nullable = false)
    private CreditCard creditCard;

    @Digits(integer = 9, fraction = 2)
    @Column(nullable = false)
    private double ammount;

    @JsonIgnore
    @OneToOne(optional = false, fetch = FetchType.EAGER)
    private Request request;
}
