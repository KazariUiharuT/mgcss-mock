package com.acme.bnb.services;

import com.acme.bnb.controlers.clases.ActorRegisterData;
import com.acme.bnb.exceptions.InvalidParametersException;
import com.acme.bnb.model.Commentable;
import com.acme.bnb.model.Lessor;
import com.acme.bnb.model.datatype.CreditCard;
import com.acme.bnb.repositories.LessorRepository;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Data
@Service
@Transactional
public class LessorService {

    private final LessorRepository lessorRepo;
    private final ActorService actorService;
    private final CommentableService commentableService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final B0vEBlobService blobService;

    public Optional<Lessor> findById(Long id) {
        return lessorRepo.findById(id);
    }

    public Long register(ActorRegisterData registerData) {
        if(!registerData.isValid()) throw new InvalidParametersException("Empty parameters are not allowed");
        if(actorService.findByEmail(registerData.getEmail()).isPresent()) throw new InvalidParametersException("Email already in use");
        
        Commentable commenatable = new Commentable();
        commentableService.save(commenatable);
        
        Lessor lessor = new Lessor();
        lessor.setName(registerData.getName());
        lessor.setSurname(registerData.getSurname());
        lessor.setEmail(registerData.getEmail());
        lessor.setPwd(bCryptPasswordEncoder.encode(registerData.getPwd()));
        lessor.setCommentable(commenatable);
        
        lessorRepo.save(lessor);
        return lessor.getId();
    }

    public Lessor getLessor(Long id) {
        return lessorRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Lessor not found"));
    }

    public List<Lessor> findAll() {
        return (List<Lessor>) lessorRepo.findAll();
    }

    public void updateCreditCard(Long id, CreditCard creditCard) {
        if(!creditCard.isValid()) throw new InvalidParametersException("Invalid creditcard");
        Lessor lessor = getLessor(id);
        lessor.setCreditCard(creditCard);
        lessorRepo.save(lessor);
    }
}
