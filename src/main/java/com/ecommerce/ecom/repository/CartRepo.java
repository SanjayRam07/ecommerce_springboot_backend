package com.ecommerce.ecom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.ecom.model.Cart;
import com.ecommerce.ecom.model.User;

@Repository
public interface CartRepo extends JpaRepository<Cart,Integer>{

    List<Cart> findAllByUserOrderByCreatedDateAsc(User user);
    
}
