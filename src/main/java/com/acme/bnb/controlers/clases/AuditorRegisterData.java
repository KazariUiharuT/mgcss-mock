package com.acme.bnb.controlers.clases;

import lombok.Data;
import org.apache.commons.lang.StringUtils;

@Data
public class AuditorRegisterData extends ActorRegisterData {
    private String company;

    public void setCompany(String company) {
        this.company = company.trim();
    }

    @Override
    public boolean isValid() {
        return super.isValid() && !(StringUtils.isBlank(company));
    }
    
    
}
