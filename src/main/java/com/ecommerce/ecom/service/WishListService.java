package com.ecommerce.ecom.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ecommerce.ecom.common.ApiResponse;
import com.ecommerce.ecom.dto.ProductDto;
import com.ecommerce.ecom.model.Product;
import com.ecommerce.ecom.model.User;
import com.ecommerce.ecom.model.WishList;
import com.ecommerce.ecom.repository.WishListRepo;

@Service
public class WishListService {

    @Autowired
    WishListRepo wishListRepo;

    @Autowired
    ProductService productService;

    public ResponseEntity<ApiResponse> createWishList(WishList wishlist) {
        WishList wishList2 = wishListRepo.findByUserAndProduct(wishlist.getUser(), wishlist.getProduct());
        if( Objects.nonNull(wishList2)) {
            return new ResponseEntity<>(new ApiResponse(false, "product already exists under this user"),HttpStatus.NOT_ACCEPTABLE);
        }
        wishListRepo.save(wishlist);
        return new ResponseEntity<>(new ApiResponse(true, "product added to wishlist"),HttpStatus.CREATED);
    }

    public ResponseEntity<List<ProductDto>> getWishListForUser(User user) {
        List<WishList> wishLists = wishListRepo.findAllByUserOrderByCreatedDateDesc(user);
        List<ProductDto> productDtos = new ArrayList<>();

        for(WishList wishList : wishLists) {
            ProductDto productDto = productService.getProductDto(wishList.getProduct());
            productDtos.add(productDto);
        }

        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }

}
