package com.acme.bnb.repositories;

import com.acme.bnb.model.Actor;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActorRepository extends CrudRepository<Actor, Long>{

    public Optional<Actor> findByEmail(String email);
    
}
