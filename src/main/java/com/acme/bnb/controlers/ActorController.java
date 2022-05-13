package com.acme.bnb.controlers;

import com.acme.bnb.controlers.clases.ActorUpdateData;
import com.acme.bnb.controlers.clases.LongWrapper;
import com.acme.bnb.controlers.clases.SocialIdentityCreationData;
import com.acme.bnb.model.Actor;
import com.acme.bnb.model.SocialIdentity;
import com.acme.bnb.services.ActorService;
import com.acme.bnb.services.AuthService;
import com.acme.bnb.services.SocialIdentityService;
import java.util.List;
import lombok.Data;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Data
@RestController()
@RequestMapping(path = "api/v1/actor")
public class ActorController {

    private final AuthService authService;
    private final SocialIdentityService socialIdentityService;
    private final ActorService actorService;

    @GetMapping("/{actorId}")
    public Actor get(@PathVariable Long actorId) {
        return actorService.getActorWithStars(actorId);
    }

    @PutMapping("/{actorId}")
    @Secured("actor")
    public void update(@PathVariable Long actorId, @RequestBody ActorUpdateData updateData) {
        if(!authService.checkAuthById(actorId)) throw new AccessDeniedException("Cannot modify other users");
        actorService.update(actorId, updateData);
    }
    
    @GetMapping("/{actorId}/social-identity")
    public List<SocialIdentity> listSocialIdentities(@PathVariable Long actorId) {
        return socialIdentityService.findByActor(actorId);
    }

    @PostMapping("/{actorId}/social-identity")
    @Secured("actor")
    public LongWrapper createSocialIdentity(@PathVariable Long actorId, @RequestBody SocialIdentityCreationData createData) {
        if (!authService.checkAuthById(actorId)) {
            throw new AccessDeniedException("Cannot modify other users");
        }
        return new LongWrapper(socialIdentityService.create(actorId, createData));
    }
}
