package com.acme.bnb.model;

import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Entity
public class Audit extends DomainEntity {

    @Column(nullable = false, columnDefinition = "text")
    private String text;

    @Column(nullable = false)
    private boolean draft;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Date date;

    @ManyToOne(optional = false)
    private Property property;

    @ManyToOne(optional = false)
    private Auditor author;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "audit", cascade = CascadeType.REMOVE)
    private Collection<AuditAttachment> attachments;

}
