package com.acme.bnb.repositories;

import com.acme.bnb.model.Auditor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditorRepository extends CrudRepository<Auditor, Long>{
    
}
