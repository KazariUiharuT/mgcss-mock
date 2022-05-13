package com.acme.bnb.services;

import com.acme.bnb.controlers.clases.ActorUpdateData;
import com.acme.bnb.exceptions.InvalidParametersException;
import com.acme.bnb.model.Actor;
import com.acme.bnb.model.Administrator;
import com.acme.bnb.model.Auditor;
import com.acme.bnb.model.Lessor;
import com.acme.bnb.model.Tenant;
import com.acme.bnb.repositories.ActorRepository;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
@Transactional
public class ActorService {
    
    private final ActorRepository actorRepo;
    private final CommentService commentService;
    private final B0vEBlobService blobService;
    
    public Optional<Actor> findByEmail(String email){
        return actorRepo.findByEmail(email);
    }
    
    public Actor findById(Long actorId) {
        return actorRepo.findById(actorId).orElseThrow(() -> new IllegalStateException("User not found"));
    }

    public Actor getActor(Long actorId) {
         Actor actor = actorRepo.findById(actorId).orElseThrow(() -> new IllegalStateException("User not found"));
         if(actor instanceof Lessor)
            actor.setType("lessor");
         else if(actor instanceof Tenant)
            actor.setType("tenant");
         else if(actor instanceof Auditor)
            actor.setType("auditor");
         else if(actor instanceof Administrator)
            actor.setType("admin");
         return actor;
    }
    
    public Actor getActorWithStars(Long actorId) {
         Actor actor = getActor(actorId);
         if(actor instanceof Lessor)
            actor.setStars(commentService.getAvgStarsByActor(((Lessor)actor).getCommentable()));
         else if(actor instanceof Tenant)
            actor.setStars(commentService.getAvgStarsByActor(((Tenant)actor).getCommentable()));
         return actor;
    }

    public void update(Long id, ActorUpdateData updateData) {
        if(!updateData.isValid()) throw new InvalidParametersException("Empty parameters are not allowed");
        if(updateData.getPhone() != null && !updateData.getPhone().isValid()) throw new InvalidParametersException("Phone is not valid");
        
        Actor actor = actorRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        actor.setName(updateData.getName());
        actor.setSurname(updateData.getSurname());
        actor.setPhone(updateData.getPhone());
        if(updateData.getPicture() != null){
            String newPicture = blobService.storeBlob(updateData.getPicture(), true, true);
            if(newPicture == null) throw new IllegalStateException("Could not upload image to blob service");
            actor.setPicture(newPicture);
        }
        
        actorRepo.save(actor);
    }
}
