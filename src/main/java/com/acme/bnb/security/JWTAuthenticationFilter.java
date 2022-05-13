package com.acme.bnb.security;

import com.acme.bnb.model.Actor;
import com.acme.bnb.services.ActorService;
import com.acme.bnb.services.ConstantsService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final ConstantsService constantsService;
    private final ActorService actorService;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, ApplicationContext context) {
        this.authenticationManager = authenticationManager;
        this.constantsService = (ConstantsService) context.getBean(ConstantsService.class);
        this.actorService = (ActorService) context.getBean(ActorService.class);

        setFilterProcessesUrl("/api/v1/services/controller/user/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        try {
            LoginCredentials creds = new ObjectMapper().readValue(req.getInputStream(), LoginCredentials.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getEmail(),
                            creds.getPwd())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException {
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) auth.getPrincipal();
        String token = JWT.create()
                .withSubject(user.getUsername())
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .withExpiresAt(new Date(System.currentTimeMillis() + constantsService.getEXPIRATION_TIME()))
                .sign(Algorithm.HMAC512(constantsService.getAPP_SECRET().getBytes()));

        Actor actorData = actorService.findByEmail(user.getUsername()).get();
        String userType = user.getAuthorities().stream().map(a -> a.getAuthority()).filter(a -> !a.equals("actor")).findAny().get();

        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        res.getWriter().write(
                "{\"jwt\":\"" + token + "\", \"userId\":\""+actorData.getId()+"\", \"userType\":\""+userType+"\"}"
        );
        res.getWriter().flush();
    }
}
