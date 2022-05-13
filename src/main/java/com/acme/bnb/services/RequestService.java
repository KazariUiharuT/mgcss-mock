package com.acme.bnb.services;

import com.acme.bnb.controlers.clases.RequestAcceptData;
import com.acme.bnb.controlers.clases.RequestCreationData;
import com.acme.bnb.exceptions.ConflictException;
import com.acme.bnb.exceptions.InvalidParametersException;
import com.acme.bnb.model.Actor;
import com.acme.bnb.model.Lessor;
import com.acme.bnb.model.Property;
import com.acme.bnb.model.Request;
import com.acme.bnb.model.Tenant;
import com.acme.bnb.model.enums.RequestState;
import com.acme.bnb.repositories.RequestRepository;
import java.util.List;
import javax.transaction.Transactional;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
@Transactional
public class RequestService {
    
    private final RequestRepository requestRepo;
    private final AuthService authService;
    private final ActorService actorService;
    private final PropertyService propertyService;
    private final SysConfigService sysConfigService;
    
    public List<Request> findRequestsByTenant(Long tenantId){
        List<Request> requests = requestRepo.findRequestsByTenantIdOrderByDateDesc(tenantId);
        
        Actor auth = authService.getAuth();
        for (Request request : requests) {
            if(!request.getProperty().getPropietary().getId().equals(auth.getId()))
                request.setLessorCreditCard(null);
            else
                if(request.getLessorCreditCard()!= null) request.getLessorCreditCard().hide();
            if(!request.getTenant().getId().equals(auth.getId()))
                request.setTenantCreditCard(null);
            else
                if(request.getTenantCreditCard()!= null) request.getTenantCreditCard().hide();
        }
        
        return requests;
    }

    public List<Request> findRequestsByProperty(Long propertyId) {
        return requestRepo.findRequestsByPropertyIdOrderByDateDesc(propertyId);
    }

    public Long create(Long propertyId, RequestCreationData creationData) {
        if(!creationData.isValid()) throw new InvalidParametersException("Invalid request parameters");
        Tenant tenant = (Tenant) authService.getAuth();
        Property property = propertyService.getProperty(propertyId);
        
        if(creationData.getCreditCard()== null && tenant.getCreditCard() != null && tenant.getCreditCard().isValid())
             creationData.setCreditCard(tenant.getCreditCard());
        if(creationData.getCreditCard() == null || !creationData.getCreditCard().isValid()) throw new InvalidParametersException("Invalid creditcard");
        
        if(creationData.getCheckIn().compareTo(creationData.getCheckOut()) > 0) throw new InvalidParametersException("Check out date cannot be sooner than check in");
        
        Request request = new Request();
        request.setCheckIn(creationData.getCheckIn());
        request.setCheckOut(creationData.getCheckOut());
        request.setSmoker(creationData.isSmoker());
        request.setTenantCreditCard(creationData.getCreditCard());
        request.setProperty(property);
        request.setRate(property.getRate());
        request.setTenantFee(Double.parseDouble(sysConfigService.getConfig("feeTenant")));
        request.setLessorFee(Double.parseDouble(sysConfigService.getConfig("feeLessor")));
        request.setStatus(RequestState.PENDING);
        request.setTenant(tenant);
        
        if(request.getNDays() < 1) throw new InvalidParametersException("Check in and check out cannot be the same day"); 
        
        propertyService.incrementRequests(property);
        
        requestRepo.save(request);
        return request.getId();
    }

    public void accept(Long requestId, RequestAcceptData acceptData) {
        Lessor lessor = (Lessor) authService.getAuth();
        Request request = findById(requestId);
        if(!request.getStatus().equals(RequestState.PENDING)) throw new ConflictException("This request is not pending");
        
        if(acceptData.isAccept()){
            if(acceptData.getCreditCard() == null && lessor.getCreditCard() != null) acceptData.setCreditCard(lessor.getCreditCard());
            if(acceptData.getCreditCard() == null || !acceptData.getCreditCard().isValid()) throw new InvalidParametersException("Invalid creditcard");
            request.setLessorCreditCard(acceptData.getCreditCard());
            request.setStatus(RequestState.ACCEPTED);
        }else{
            request.setStatus(RequestState.DENIED);
        }
        
        requestRepo.save(request);
    }

    public Request findById(Long requestId) {
        return requestRepo.findById(requestId).orElseThrow(() -> new IllegalStateException("Request not found"));
    }

    public List<Request> findRequestsByPropietary(Long lessorId) {
        return requestRepo.findAllByProperty_PropietaryId(lessorId);
    }
}
