package com.acme.bnb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

@Data
@Entity
public class Comment extends DomainEntity {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "text")
    private String text;

    @Min(0)
    @Max(5)
    @Column(nullable = false)
    private int stars;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Date date;

    @JsonIgnore
    @ManyToOne(optional = false)
    private Commentable target;

    @ManyToOne(optional = false)
    private Actor author;
}
