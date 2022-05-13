package com.acme.bnb.services;

import com.acme.bnb.model.PropertyPicture;
import com.acme.bnb.repositories.PropertyPictureRepository;
import javax.transaction.Transactional;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
@Transactional
public class PropertyPictureService {
    
    private final PropertyPictureRepository propertyPictureRepo;

    public void deleteBypropertyId(Long propertyId) {
        propertyPictureRepo.deleteBypropertyId(propertyId);
    }

    public void save(PropertyPicture p) {
        propertyPictureRepo.save(p);
    }

    
}
