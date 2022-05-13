package com.acme.bnb.services;

import com.acme.bnb.model.Actor;
import com.acme.bnb.model.Administrator;
import com.acme.bnb.repositories.ActorRepository;
import javax.transaction.Transactional;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Data
@Service
@Transactional
public class AuthService {
    
    private final ActorRepository actorRepo;
    
    public boolean checkAuthById(Long id){
        return getAuth().getId().equals(id);
    }
    
    public boolean isAuthAdmin(){
        return getAuth() instanceof Administrator;
    }
    
    public Actor getAuth(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return actorRepo.findByEmail((String) auth.getPrincipal()).orElseThrow(() -> new IllegalStateException("Auth not found"));
    }

}
