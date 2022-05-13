package com.acme.bnb.repositories;

import com.acme.bnb.model.PropertyAttributeValue;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyAttributeValueRepository extends CrudRepository<PropertyAttributeValue, Long>{

    public void deleteByPropertyId(Long propertyId);
    
}
