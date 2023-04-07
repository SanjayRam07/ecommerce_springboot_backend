package com.ecommerce.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.ecom.model.User;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {
    
    User findByEmail(String email);

}
