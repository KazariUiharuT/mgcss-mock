package com.acme.bnb.repositories;

import com.acme.bnb.model.Lessor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessorRepository extends CrudRepository<Lessor, Long>{
    
}
