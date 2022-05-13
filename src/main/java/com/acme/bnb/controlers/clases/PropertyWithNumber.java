package com.acme.bnb.controlers.clases;

import com.acme.bnb.model.Property;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PropertyWithNumber {

    private Property property;
    private double value;

    public PropertyWithNumber(Property property, Float value) {
        this.property = property;
        this.value = value == null ? 0 : value;
    }
    
    public PropertyWithNumber(Property property, Double value) {
        this.property = property;
        this.value = value == null ? 0 : value;
    }

    public PropertyWithNumber(Property property, Integer value) {
        this.property = property;
        this.value = value == null ? 0 : value;
    }
    
    public PropertyWithNumber(Property property, Long value) {
        this.property = property;
        this.value = value == null ? 0 : value;
    }
    
}
