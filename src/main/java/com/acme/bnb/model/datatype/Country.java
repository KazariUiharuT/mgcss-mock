package com.acme.bnb.model.datatype;

import javax.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Country {
    
    @Column(length = 100)
    private String name;
    
    @Column(length = 2)
    private String iso2;
    
    @Column(length = 3)
    private String iso3;
    
    private int code;
    
    
}
