package com.acme.bnb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Version;
import lombok.Data;

@Data
@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS) //Necesario porque si no, Actor no puede tener JOINED y se cae toda la lógica
public abstract class DomainEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @JsonIgnore
    @Version
    private int version;
}
