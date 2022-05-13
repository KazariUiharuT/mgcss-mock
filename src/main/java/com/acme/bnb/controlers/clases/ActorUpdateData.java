package com.acme.bnb.controlers.clases;

import com.acme.bnb.model.datatype.Phone;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

@Data
public class ActorUpdateData implements Validable {
    private String name;
    private String surname;
    private Phone phone;
    private String picture;

    public void setName(String name) {
        this.name = name.trim();
    }

    public void setSurname(String surname) {
        this.surname = surname.trim();
    }

    @Override
    public boolean isValid() {
        return !(
                StringUtils.isBlank(name) ||
                StringUtils.isBlank(surname)
        );
    }
}
