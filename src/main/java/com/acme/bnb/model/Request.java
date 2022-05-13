package com.acme.bnb.model;

import com.acme.bnb.model.datatype.CreditCard;
import com.acme.bnb.model.enums.RequestState;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Entity
public class Request extends DomainEntity {

    @Column(nullable = false)
    private boolean smoker;

    @Column(nullable = false)
    private Date checkIn;

    @Column(nullable = false)
    private Date checkOut;

    @Column(nullable = false)
    private RequestState status;
    
    @Digits(integer = 9, fraction = 2)
    @Column(nullable = false)
    private double rate;

    @Embedded
    @Column(nullable = true)
    private CreditCard lessorCreditCard;

    @Embedded
    @Column(nullable = false)
    private CreditCard tenantCreditCard;

    @Digits(integer = 9, fraction = 2)
    @Column(nullable = false)
    private double lessorFee;

    @Digits(integer = 9, fraction = 2)
    @Column(nullable = false)
    private double tenantFee;

    @ManyToOne(optional = false)
    private Property property;

    @ManyToOne(optional = false)
    private Tenant tenant;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToOne(mappedBy = "request", optional = true, cascade = CascadeType.REMOVE)
    private Invoice invoice;
    
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Date date;

    @Transient
    @JsonSerialize
    public long getNDays(){
        return ChronoUnit.DAYS.between(checkIn.toInstant(), checkOut.toInstant());
    }
    
    @Transient
    @JsonSerialize
    public double getLessorTotal(){
        return (getNDays()*rate)-lessorFee;
    }
    
    @Transient
    @JsonSerialize
    public double getTenantTotal(){
        return (getNDays()*rate)+tenantFee;
    }

}
