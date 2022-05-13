package com.acme.bnb.repositories;

import com.acme.bnb.model.PropertyAttribute;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyAttributeRepository extends CrudRepository<PropertyAttribute, Long>{

    public Optional<PropertyAttribute> findByName(String country);

    public List<PropertyAttribute> findBySysDefaultTrue();
    
}
