package com.ecommerce.ecom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecom.common.ApiResponse;
import com.ecommerce.ecom.dto.ProductDto;
import com.ecommerce.ecom.dto.cart.AddToCartDto;
import com.ecommerce.ecom.dto.cart.CartDto;
import com.ecommerce.ecom.exceptions.ProductNotExistException;
import com.ecommerce.ecom.model.User;
import com.ecommerce.ecom.service.AuthenticationService;
import com.ecommerce.ecom.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private AuthenticationService authenticationService;

    // add to cart
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToCart(@RequestBody AddToCartDto addToCartDto,
            @RequestParam("token") String token) throws ProductNotExistException {
        // authenticate the token
        authenticationService.authenticate(token);

        // find the user
        User user = authenticationService.getUser(token);

        cartService.addToCart(addToCartDto, user);

        return new ResponseEntity<>(new ApiResponse(true, "added to cart"), HttpStatus.OK);
    }

    // get all from cart for a user
    @GetMapping("/listCart")
    public ResponseEntity<CartDto> getCartItems(@RequestParam("token") String token) {
        // authenticate the token
        authenticationService.authenticate(token);

        // find the user
        User user = authenticationService.getUser(token);

        CartDto cartDto = cartService.listCartItems(user);

        return new ResponseEntity<CartDto>(cartDto, HttpStatus.OK);
    }

    // delete from cart
    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable("cartItemId") Integer itemId, @RequestParam("token") String token) {
        // authenticate the token
        authenticationService.authenticate(token);

        // find the user
        User user = authenticationService.getUser(token);

        cartService.deleteCartItem(itemId, user);

        return new ResponseEntity<>(new ApiResponse(true, "deleted sccessfully"),HttpStatus.OK);
    }
}
