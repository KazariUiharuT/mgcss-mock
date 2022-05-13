package com.acme.bnb.services;

import com.acme.bnb.controlers.clases.PropertyAttributeValueData;
import com.acme.bnb.controlers.clases.PropertyData;
import com.acme.bnb.exceptions.InvalidParametersException;
import com.acme.bnb.model.Lessor;
import com.acme.bnb.model.Property;
import com.acme.bnb.model.PropertyAttribute;
import com.acme.bnb.model.PropertyAttributeValue;
import com.acme.bnb.model.PropertyPicture;
import com.acme.bnb.repositories.PropertyRepository;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
@Transactional
public class PropertyService {
    
    private final PropertyRepository propertyRepo;
    private final AuthService authService;
    private final ActorService actorService;
    private final CommentService commentService;
    private final PropertyAttributeService propertyAttributeService;
    private final PropertyAttributeValueService propertyAttributeValueService;
    private final PropertyPictureService propertyPictureService;
    private final ConstantsService constantsService;
    private final B0vEBlobService b0vEBlobService;
    
    public List<Property> findPropertiesByPropietary(Long propietaryId){
        return propertyRepo.findPropertiesByPropietaryIdOrderByDateDesc(propietaryId);
    }

    public List<Property> findAll() {
        return (List<Property>) propertyRepo.findAllByOrderByDateDesc();
    }

    public Property getProperty(Long id) {
        Property property = propertyRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Property not found"));
        property.setPropietary((Lessor) actorService.getActorWithStars(property.getPropietary().getId()));
        return property;
    }

    public Long create(PropertyData creationData) {
        if(!creationData.isValid()) throw new InvalidParametersException("Invalid request parameters");
        if(creationData.getPictures().length > constantsService.getMAX_PICTURES_PER_PROPERTY()) throw new InvalidParametersException("Exceded maximun number of pictures per property");
        Lessor lessor = (Lessor) authService.getAuth();
        
        List<PropertyAttributeValueData> rawProperties = List.of(creationData.getAttributes());
        List<PropertyAttribute> requiredParameters = propertyAttributeService.findAllSysDefault();
        if(requiredParameters.stream().anyMatch(p -> rawProperties.stream().noneMatch(v -> v.getId().equals(p.getId())))) throw new InvalidParametersException("Missing required parameters");
        
        Property property = new Property();
        property.setName(creationData.getName());
        property.setDescription(creationData.getDescription());
        property.setAddress(creationData.getAddress());
        property.setRate(creationData.getRate());
        property.setPropietary(lessor);
        
        propertyRepo.save(property);
        
        rawProperties.stream().map(v -> new PropertyAttributeValue(v.getValue(), propertyAttributeService.getPropertyAttribute(v.getId()), property)).forEach(a -> propertyAttributeValueService.save(a));
        
        List<String> rawPictures = List.of(creationData.getPictures());
        rawPictures.stream().map(b -> new PropertyPicture(b0vEBlobService.storePic(b), property)).forEach(p -> propertyPictureService.save(p));
        
        return property.getId();
    }

    public void update(Long propertyId, PropertyData updatedData) {
        if(!updatedData.isValid()) throw new InvalidParametersException("Invalid request parameters");
        if(updatedData.getPictures().length > constantsService.getMAX_PICTURES_PER_PROPERTY()) throw new InvalidParametersException("Exceded maximun number of pictures per property");
        Property property = getProperty(propertyId);
        
        List<PropertyAttributeValueData> rawProperties = List.of(updatedData.getAttributes());
        List<PropertyAttribute> requiredParameters = propertyAttributeService.findAllSysDefault();
        if(requiredParameters.stream().anyMatch(p -> rawProperties.stream().noneMatch(v -> v.getId().equals(p.getId())))) throw new InvalidParametersException("Missing required parameters");
        
        property.setName(updatedData.getName());
        property.setDescription(updatedData.getDescription());
        property.setAddress(updatedData.getAddress());
        property.setRate(updatedData.getRate());
        
        propertyRepo.save(property);
        
        propertyAttributeValueService.deleteByPropertyId(propertyId);
        rawProperties.stream().map(v -> new PropertyAttributeValue(v.getValue(), propertyAttributeService.getPropertyAttribute(v.getId()), property)).forEach(a -> propertyAttributeValueService.save(a));
        
        if(updatedData.getPictures().length > 0){
            propertyPictureService.deleteBypropertyId(propertyId);
            List<String> rawPictures = List.of(updatedData.getPictures());
            rawPictures.stream().map(b -> new PropertyPicture(b0vEBlobService.storePic(b), property)).forEach(p -> propertyPictureService.save(p));
        }
    }

    public void delete(Long propertyId) {
        propertyRepo.delete(getProperty(propertyId));
    }

    void incrementRequests(Property property) {
        property.setNRequests(property.getNRequests()+1);
        propertyRepo.save(property);
    }

    void incrementAudits(Property property) {
        property.setNAudits(property.getNAudits()+1);
        propertyRepo.save(property);
    }
}
