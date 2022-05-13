package com.acme.bnb.controlers.clases;

import lombok.Data;
import org.apache.commons.lang.StringUtils;

@Data
public class PropertyAttributeData implements Validable {
    private String name;

    public void setName(String name) {
        this.name = name.trim();
    }

    @Override
    public boolean isValid() {
        return !(
                StringUtils.isBlank(name)
        );
    }
}
