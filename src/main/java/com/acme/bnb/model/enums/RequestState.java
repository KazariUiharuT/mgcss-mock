package com.acme.bnb.model.enums;

public enum RequestState {
    PENDING(1),
    ACCEPTED(2),
    DENIED(3);
    
    private final int value;
    
    private RequestState(int value){
        this.value = value;
    }
    public int getValue(){
        return value;
    }
    
    @Override
    public String toString() {
         return String.valueOf(value);
    }
}
