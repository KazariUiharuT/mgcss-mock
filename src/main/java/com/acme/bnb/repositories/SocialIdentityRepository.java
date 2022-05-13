package com.acme.bnb.repositories;

import com.acme.bnb.model.SocialIdentity;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialIdentityRepository extends CrudRepository<SocialIdentity, Long>{

    public List<SocialIdentity> findByActorId(Long actorId);
    
}
