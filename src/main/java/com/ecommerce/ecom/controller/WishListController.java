package com.ecommerce.ecom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecom.common.ApiResponse;
import com.ecommerce.ecom.dto.ProductDto;
import com.ecommerce.ecom.model.Product;
import com.ecommerce.ecom.model.User;
import com.ecommerce.ecom.model.WishList;
import com.ecommerce.ecom.repository.WishListRepo;
import com.ecommerce.ecom.service.AuthenticationService;
import com.ecommerce.ecom.service.WishListService;

@RestController
@RequestMapping("/wishlist")
public class WishListController {

    @Autowired
    WishListRepo wishListRepo;

    @Autowired
    WishListService wishListService;

    @Autowired
    AuthenticationService authenticationService;

    // save product
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToWishList(@RequestBody Product product, @RequestParam("token") String token) {
        // authenticate the token
        authenticationService.authenticate(token);

        // find the user
        User user = authenticationService.getUser(token);

        // save the product
        WishList wishlist = new WishList(product, user);

        return wishListService.createWishList(wishlist);
    }

    // get all products in wishlist
    @GetMapping("/getWishList/{token}")
    public ResponseEntity<List<ProductDto>> getWishList(@PathVariable("token") String token) {
        // authenticate the token
        authenticationService.authenticate(token);

        // find the user
        User user = authenticationService.getUser(token);

        return wishListService.getWishListForUser(user);
    }
    
}
