package com.acme.bnb.repositories;

import com.acme.bnb.model.PropertyPicture;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyPictureRepository extends CrudRepository<PropertyPicture, Long>{

    public void deleteBypropertyId(Long propertyId);
    
}
