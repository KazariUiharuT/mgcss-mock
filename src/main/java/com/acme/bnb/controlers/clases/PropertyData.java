package com.acme.bnb.controlers.clases;

import lombok.Data;
import org.apache.commons.lang.StringUtils;

@Data
public class PropertyData implements Validable {

    private String name;
    private String description;
    private double rate;
    private String address;
    private String[] pictures;
    private PropertyAttributeValueData[] attributes;

    public void setName(String name) {
        this.name = name.trim();
    }

    public void setDescription(String description) {
        this.description = description.trim();
    }

    public void setAddress(String address) {
        this.address = address.trim();
    }

    @Override
    public boolean isValid() {
        return !(StringUtils.isBlank(name)
                || StringUtils.isBlank(description)
                || StringUtils.isBlank(address)
                || rate < 0);
    }
}
