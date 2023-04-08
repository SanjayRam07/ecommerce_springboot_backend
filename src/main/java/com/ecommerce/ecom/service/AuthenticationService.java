package com.ecommerce.ecom.service;

import java.util.Objects;

import javax.naming.AuthenticationException;

import org.apache.tomcat.websocket.AuthenticatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.ecom.exceptions.AuthenticationFailException;
import com.ecommerce.ecom.model.AuthenticationToken;
import com.ecommerce.ecom.model.User;
import com.ecommerce.ecom.repository.TokenRepo;

@Service
public class AuthenticationService {

    @Autowired
    TokenRepo tokenRepo;

    public void saveConfirmationToken(AuthenticationToken authenticationToken) {
        tokenRepo.save(authenticationToken);
    }

    public AuthenticationToken getToken(User user) {
        return tokenRepo.findByUser(user);
    }

    public void authenticate(String token) {
        // null check
        if(Objects.isNull(token)) {
            throw new AuthenticationFailException("token not present");
        }
        if(Objects.isNull(getUser(token))) {
            throw new AuthenticationFailException("invalid token");
        }
    }

    public User getUser(String token) {
        final AuthenticationToken authenticationToken = tokenRepo.findByToken(token);

        if(Objects.isNull(authenticationToken)) {
            return null;
        }
        return authenticationToken.getUser();
    }
    
}
