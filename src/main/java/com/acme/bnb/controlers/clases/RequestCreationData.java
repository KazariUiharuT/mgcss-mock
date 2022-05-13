package com.acme.bnb.controlers.clases;

import com.acme.bnb.model.datatype.CreditCard;
import java.util.Date;
import lombok.Data;

@Data
public class RequestCreationData implements Validable {

    private boolean smoker;
    
    private Date checkIn;
    
    private Date checkOut;
    
    private CreditCard creditCard;

    @Override
    public boolean isValid() {
        return !(checkIn == null || checkOut == null || checkIn == null || checkOut == null);
    }
}
