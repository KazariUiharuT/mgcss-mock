package com.acme.bnb.services;

import com.acme.bnb.model.PropertyAttributeValue;
import com.acme.bnb.repositories.PropertyAttributeValueRepository;
import javax.transaction.Transactional;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
@Transactional
public class PropertyAttributeValueService {
    
    private final PropertyAttributeValueRepository propertyAttributeValueRepo;

    public void save(PropertyAttributeValue a) {
        propertyAttributeValueRepo.save(a);
    }

    public void deleteByPropertyId(Long propertyId) {
        propertyAttributeValueRepo.deleteByPropertyId(propertyId);
    }
    
}
