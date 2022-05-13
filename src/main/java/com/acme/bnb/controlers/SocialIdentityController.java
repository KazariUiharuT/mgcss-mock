package com.acme.bnb.controlers;

import com.acme.bnb.controlers.clases.SocialIdentityUpdateData;
import com.acme.bnb.services.AuthService;
import com.acme.bnb.services.SocialIdentityService;
import lombok.Data;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Data
@RestController()
@RequestMapping(path = "api/v1/social-identity")
public class SocialIdentityController {
    
    private final AuthService authService;
    private final SocialIdentityService socialIdentityService;
    
    @PutMapping("/{socialId}")
    @Secured("actor")
    public void update(@PathVariable Long socialId, @RequestBody SocialIdentityUpdateData updateData) {
        if(!socialIdentityService.findById(socialId).getActor().getId().equals(authService.getAuth().getId())) throw new AccessDeniedException("This identity does not belong to user");
        socialIdentityService.update(socialId, updateData);
    }
    
    @DeleteMapping("/{socialId}")
    @Secured("actor")
    public void delete(@PathVariable Long socialId) {
        if(!socialIdentityService.findById(socialId).getActor().getId().equals(authService.getAuth().getId())) throw new AccessDeniedException("This identity does not belong to user");
        socialIdentityService.delete(socialId);
    }
}
