package com.acme.bnb.controlers.clases;

import com.acme.bnb.model.Actor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ActorWithNumber {

    private Actor actor;
    private double value;

    public ActorWithNumber(Actor actor, Float value) {
        this.actor = actor;
        this.value = value == null ? 0 : value;
    }
    
    public ActorWithNumber(Actor actor, Double value) {
        this.actor = actor;
        this.value = value == null ? 0 : value;
    }
    
    public ActorWithNumber(Actor actor, Integer value) {
        this.actor = actor;
        this.value = value == null ? 0 : value;
    }
    
    public ActorWithNumber(Actor actor, Long value) {
        this.actor = actor;
        this.value = value == null ? 0 : value;
    }

}
