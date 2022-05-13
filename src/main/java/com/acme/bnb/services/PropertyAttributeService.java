package com.acme.bnb.services;

import com.acme.bnb.controlers.clases.PropertyAttributeData;
import com.acme.bnb.exceptions.ConflictException;
import com.acme.bnb.model.PropertyAttribute;
import com.acme.bnb.repositories.PropertyAttributeRepository;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
@Transactional
public class PropertyAttributeService {
    
    private final PropertyAttributeRepository properAttributeRepo;
    
    public List<PropertyAttribute> findAll(){
        return (List<PropertyAttribute>) properAttributeRepo.findAll();
    }
    public List<PropertyAttribute> findAllSysDefault(){
        return properAttributeRepo.findBySysDefaultTrue();
    }
    
    public PropertyAttribute getPropertyAttribute(Long id) {
        return properAttributeRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Property Attribute not found"));
    }

    public Long create(PropertyAttributeData data) {
        PropertyAttribute attribute = new PropertyAttribute(data.getName(), false);
        properAttributeRepo.save(attribute);
        return attribute.getId();
    }

    public void update(Long attributeId, PropertyAttributeData data) {
        PropertyAttribute attribute = getPropertyAttribute(attributeId);
        attribute.setName(data.getName());
        properAttributeRepo.save(attribute);
    }

    public void delete(Long attributeId) {
        PropertyAttribute attribute = getPropertyAttribute(attributeId);
        if(attribute.isSysDefault()) throw new ConflictException("Can not delete system default attribute");
        properAttributeRepo.delete(attribute);
    }
}
