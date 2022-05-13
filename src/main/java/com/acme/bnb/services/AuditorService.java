package com.acme.bnb.services;

import com.acme.bnb.controlers.clases.ActorUpdateData;
import com.acme.bnb.controlers.clases.AuditorRegisterData;
import com.acme.bnb.exceptions.InvalidParametersException;
import com.acme.bnb.model.Actor;
import com.acme.bnb.model.Administrator;
import com.acme.bnb.model.Auditor;
import com.acme.bnb.repositories.AuditorRepository;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import lombok.Data;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Data
@Service
@Transactional
public class AuditorService {

    private final AuditorRepository auditorRepo;
    private final AuthService authService;
    private final ActorService actorService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final B0vEBlobService blobService;

    public Optional<Auditor> findById(Long id) {
        return auditorRepo.findById(id);
    }

    public Long register(AuditorRegisterData registerData) {
        if(!registerData.isValid()) throw new InvalidParametersException("Empty parameters are not allowed");
        if(actorService.findByEmail(registerData.getEmail()).isPresent()) throw new InvalidParametersException("Email already in use");
        
        Auditor auditor = new Auditor();
        auditor.setName(registerData.getName());
        auditor.setSurname(registerData.getSurname());
        auditor.setEmail(registerData.getEmail());
        auditor.setPwd(bCryptPasswordEncoder.encode(registerData.getPwd()));
        auditor.setCompany(registerData.getCompany());
        
        auditorRepo.save(auditor);
        return auditor.getId();
    }

    public Auditor getAuditor(Long id) {
        Auditor auditor = auditorRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Auditor not found"));
        
        Actor auth = authService.getAuth();
        if(!(auth instanceof Administrator) && !auth.getId().equals(id)) throw new AccessDeniedException("Cannot access this auditor");
        
        return auditor;
    }

    public void update(Long id, ActorUpdateData updateData) {
        if(!updateData.isValid()) throw new InvalidParametersException("Empty parameters are not allowed");
        if(updateData.getPhone() != null && !updateData.getPhone().isValid()) throw new InvalidParametersException("Phone is not valid");
        
        Auditor auditor = auditorRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Auditor not found"));
        auditor.setName(updateData.getName());
        auditor.setSurname(updateData.getSurname());
        auditor.setPhone(updateData.getPhone());
        if(updateData.getPicture() != null){
            blobService.storeBlob(updateData.getPicture(), true, true);
        }
    }

    public List<Auditor> listAuditors() {
        return (List<Auditor>) auditorRepo.findAll();
    }
}
