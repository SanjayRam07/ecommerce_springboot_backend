package com.ecommerce.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.ecom.model.AuthenticationToken;

@Repository
public interface TokenRepo extends JpaRepository<AuthenticationToken,Integer> {
    
}
