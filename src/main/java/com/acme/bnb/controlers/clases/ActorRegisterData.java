package com.acme.bnb.controlers.clases;

import lombok.Data;
import org.apache.commons.lang.StringUtils;

@Data
public class ActorRegisterData implements Validable {
    private String name;
    private String surname;
    private String email;
    private String pwd;

    public void setName(String name) {
        this.name = name.trim();
    }

    public void setSurname(String surname) {
        this.surname = surname.trim();
    }

    public void setEmail(String email) {
        this.email = email.trim();
    }
    
    @Override
    public boolean isValid(){
        return !(StringUtils.isBlank(name) || StringUtils.isBlank(surname) || StringUtils.isBlank(email) || StringUtils.isBlank(pwd));
    }
}
