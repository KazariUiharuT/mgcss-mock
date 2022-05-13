package com.acme.bnb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class SysConfig extends DomainEntity {

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private String value;

}
