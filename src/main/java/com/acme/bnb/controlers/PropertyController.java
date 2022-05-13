package com.acme.bnb.controlers;

import com.acme.bnb.controlers.clases.LongWrapper;
import com.acme.bnb.controlers.clases.PropertyData;
import com.acme.bnb.controlers.clases.RequestCreationData;
import com.acme.bnb.model.Audit;
import com.acme.bnb.model.Property;
import com.acme.bnb.model.Request;
import com.acme.bnb.services.AuditService;
import com.acme.bnb.services.AuthService;
import com.acme.bnb.services.PropertyService;
import com.acme.bnb.services.RequestService;
import java.util.List;
import lombok.Data;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Data
@RestController()
@RequestMapping(path = "api/v1/property")
public class PropertyController {
    
    private final AuthService authService;
    private final PropertyService propertyService;
    private final RequestService requestService;
    private final AuditService auditService;
    
    @GetMapping("")
    public List<Property> list() {
        return propertyService.findAll();
    }
    
    @GetMapping("/{propertyId}")
    public Property get(@PathVariable Long propertyId) {
        return propertyService.getProperty(propertyId);
    }

    @PostMapping("")
    @Secured("lessor")
    public LongWrapper create(@RequestBody PropertyData creationData) {
        return new LongWrapper(propertyService.create(creationData));
    }

    @PutMapping("/{propertyId}")
    @Secured("lessor")
    public void update(@PathVariable Long propertyId, @RequestBody PropertyData updatedData) {
        Property property = propertyService.getProperty(propertyId);
        if(!authService.checkAuthById(property.getPropietary().getId())) throw new AccessDeniedException("Cannot modify properties of other lessors");
        propertyService.update(propertyId, updatedData);
    }

    @DeleteMapping("/{propertyId}")
    @Secured({"lessor", "admin"})
    public void delete(@PathVariable Long propertyId) {
        Property property = propertyService.getProperty(propertyId);
        if(!authService.isAuthAdmin() && !authService.checkAuthById(property.getPropietary().getId())) throw new AccessDeniedException("Cannot delete properties of other lessors");
        propertyService.delete(propertyId);
    }

    @GetMapping("/{propertyId}/request")
    @Secured("lessor")
    public List<Request> listRequests(@PathVariable Long propertyId) {
        Property property = propertyService.getProperty(propertyId);
        if(!authService.checkAuthById(property.getPropietary().getId())) throw new AccessDeniedException("Cannot access requests of other lessors");
        return requestService.findRequestsByProperty(propertyId);
    }

    @PostMapping("/{propertyId}/request")
    @Secured("tenant")
    public LongWrapper createRequests(@PathVariable Long propertyId, @RequestBody RequestCreationData creationData) {
        return new LongWrapper(requestService.create(propertyId, creationData));
    }

    @GetMapping("/{propertyId}/audit")
    @Secured("actor")
    public List<Audit> listAudit(@PathVariable Long propertyId) {
        return auditService.findAuditsByProperty(propertyId);
    }

    @PostMapping("/{propertyId}/audit")
    @Secured("auditor")
    public LongWrapper createAudit(@PathVariable Long propertyId) {
        return new LongWrapper(auditService.create(propertyId));
    }
}
