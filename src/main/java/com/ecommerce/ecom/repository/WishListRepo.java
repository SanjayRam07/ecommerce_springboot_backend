package com.ecommerce.ecom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.ecom.model.Product;
import com.ecommerce.ecom.model.User;
import com.ecommerce.ecom.model.WishList;

@Repository
public interface WishListRepo extends JpaRepository<WishList,Integer> {

    WishList findByUserAndProduct(User user, Product product);

    List<WishList> findAllByUserOrderByCreatedDateDesc(User user);

}
