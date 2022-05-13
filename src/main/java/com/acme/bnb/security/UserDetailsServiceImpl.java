package com.acme.bnb.security;

import com.acme.bnb.model.Actor;
import com.acme.bnb.model.Administrator;
import com.acme.bnb.model.Auditor;
import com.acme.bnb.model.Lessor;
import com.acme.bnb.model.Tenant;
import com.acme.bnb.services.ActorService;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * An implementation of Spring Security's UserDetailsService.
 *
 * @author Julien Dubois
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ActorService actorService;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException, DataAccessException {
        final String email = login.toLowerCase();

        Actor user = actorService.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Email not found: " + email));

        Collection<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority("actor"));
        if (user instanceof Lessor) {
            authorities.add(new SimpleGrantedAuthority("lessor"));
        } else if (user instanceof Tenant) {
            authorities.add(new SimpleGrantedAuthority("tenant"));
        } else if (user instanceof Auditor) {
            authorities.add(new SimpleGrantedAuthority("auditor"));
        } else if (user instanceof Administrator) {
            authorities.add(new SimpleGrantedAuthority("admin"));
        } else {
            throw new IllegalStateException("Actor type not recognized");
        }

        return new org.springframework.security.core.userdetails.User(login, user.getPwd(), true, true, true, true, authorities);
    }
}
