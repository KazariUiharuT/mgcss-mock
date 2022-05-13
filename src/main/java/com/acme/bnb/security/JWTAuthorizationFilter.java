package com.acme.bnb.security;

import com.acme.bnb.services.ConstantsService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    
    private final ConstantsService constantsService;
    
    public JWTAuthorizationFilter(AuthenticationManager authManager, ApplicationContext context) {
        super(authManager);
        this.constantsService = (ConstantsService) context.getBean(ConstantsService.class);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(constantsService.getHEADER_STRING());

        if (header == null || !header.startsWith(constantsService.getTOKEN_PREFIX())) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    // Reads the JWT from the Authorization header, and then uses JWT to validate the token
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(constantsService.getHEADER_STRING());

        if (token != null) {
            // parse the token.
            try {
                DecodedJWT jwt = JWT.require(Algorithm.HMAC512(constantsService.getAPP_SECRET().getBytes()))
                        .build()
                        .verify(token.replace(constantsService.getTOKEN_PREFIX(), ""));
                String user = jwt.getSubject();
                String[] roles = jwt.getClaim("roles").asArray(String.class);
                Collection<GrantedAuthority> authorities = new ArrayList<>();
                for (String role : roles) {
                    authorities.add(new SimpleGrantedAuthority(role));
                }
                if (user != null) {
                    return new UsernamePasswordAuthenticationToken(user, null, authorities);
                }
            } catch (JWTVerificationException | IllegalArgumentException e) {
                return null;
            }

            return null;
        }

        return null;
    }
}
