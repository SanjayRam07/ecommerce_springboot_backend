package com.ecommerce.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.ecom.model.AuthenticationToken;
import com.ecommerce.ecom.model.User;

@Repository
public interface TokenRepo extends JpaRepository<AuthenticationToken,Integer> {

    AuthenticationToken findByUser(User user);
    
}
