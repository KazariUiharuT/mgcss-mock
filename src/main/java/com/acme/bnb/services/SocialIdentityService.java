package com.acme.bnb.services;

import com.acme.bnb.controlers.clases.SocialIdentityCreationData;
import com.acme.bnb.controlers.clases.SocialIdentityUpdateData;
import com.acme.bnb.model.Actor;
import com.acme.bnb.model.SocialIdentity;
import com.acme.bnb.repositories.SocialIdentityRepository;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
@Transactional
public class SocialIdentityService {
    
    private final SocialIdentityRepository socialIdentityRepo;
    private final ActorService actorService;

    public SocialIdentity findById(Long socialId) {
        return socialIdentityRepo.findById(socialId).orElseThrow(() -> new EntityNotFoundException("Social entity not found"));
    }

    public void delete(Long socialId) {
        socialIdentityRepo.deleteById(socialId);
    }

    public void update(Long socialId, SocialIdentityUpdateData updateData) {
        SocialIdentity social = findById(socialId);
        social.setNick(updateData.getNick());
        social.setUrl(updateData.getUrl());
        socialIdentityRepo.save(social);
    }

    public List<SocialIdentity> findByActor(Long actorId) {
        return socialIdentityRepo.findByActorId(actorId);
    }

    public Long create(Long actorId, SocialIdentityCreationData createData) {
        Actor actor = actorService.findById(actorId);
        SocialIdentity social = new SocialIdentity();
        social.setActor(actor);
        social.setSocialNetwork(createData.getSocialNetwork());
        social.setNick(createData.getNick());
        social.setUrl(createData.getUrl());
        socialIdentityRepo.save(social);
        return social.getId();
    }

    
    
}
