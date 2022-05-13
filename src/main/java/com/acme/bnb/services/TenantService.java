package com.acme.bnb.services;

import com.acme.bnb.controlers.clases.ActorRegisterData;
import com.acme.bnb.exceptions.InvalidParametersException;
import com.acme.bnb.model.Commentable;
import com.acme.bnb.model.Tenant;
import com.acme.bnb.model.datatype.CreditCard;
import com.acme.bnb.repositories.TenantRepository;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Data
@Service
@Transactional
public class TenantService {

    private final TenantRepository tenantRepo;
    private final ActorService actorService;
    private final CommentableService commentableService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final B0vEBlobService blobService;

    public Optional<Tenant> findById(Long id) {
        return tenantRepo.findById(id);
    }
    
    public Long register(ActorRegisterData registerData) {
        if(!registerData.isValid()) throw new InvalidParametersException("Empty parameters are not allowed");
        if(actorService.findByEmail(registerData.getEmail()).isPresent()) throw new InvalidParametersException("Email already in use");
        
        Commentable commenatable = new Commentable();
        commentableService.save(commenatable);
        
        Tenant tenant = new Tenant();
        tenant.setName(registerData.getName());
        tenant.setSurname(registerData.getSurname());
        tenant.setEmail(registerData.getEmail());
        tenant.setPwd(bCryptPasswordEncoder.encode(registerData.getPwd()));
        tenant.setCommentable(commenatable);
        
        tenantRepo.save(tenant);
        return tenant.getId();
    }

    public Tenant getTenant(Long id) {
        return tenantRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Tenant not found"));
    }

    public void updateCreditCard(Long id, CreditCard creditCard) {
        if(!creditCard.isValid()) throw new InvalidParametersException("Invalid creditcard");
        Tenant tenant = getTenant(id);
        tenant.setCreditCard(creditCard);
        tenantRepo.save(tenant);
    }

    public void updateSmoker(Long id, boolean smoker) {
        Tenant tenant = getTenant(id);
        tenant.setSmoker(smoker);
        tenantRepo.save(tenant);
    }
}
