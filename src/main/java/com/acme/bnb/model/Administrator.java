package com.acme.bnb.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Data;

@Data
@Entity
@DiscriminatorValue("admin")
public class Administrator extends Actor {

}
