package com.acme.bnb.controlers.clases;

import com.acme.bnb.model.datatype.CreditCard;
import lombok.Data;

@Data
public class RequestAcceptData implements Validable {
    private boolean accept;
    private CreditCard creditCard;

    @Override
    public boolean isValid() {
        return true;
    }
}
