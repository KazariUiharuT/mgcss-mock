package com.acme.bnb.repositories;

import com.acme.bnb.model.Property;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepository extends CrudRepository<Property, Long>{

    public List<Property> findPropertiesByPropietaryIdOrderByDateDesc(Long propietaryId);
    
    public List<Property> findAllByOrderByDateDesc();
    
}
