package com.ecommerce.ecom.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.ecom.dto.cart.AddToCartDto;
import com.ecommerce.ecom.dto.cart.CartDto;
import com.ecommerce.ecom.dto.cart.CartItemDto;
import com.ecommerce.ecom.exceptions.CustomException;
import com.ecommerce.ecom.exceptions.ProductNotExistException;
import com.ecommerce.ecom.model.Cart;
import com.ecommerce.ecom.model.Product;
import com.ecommerce.ecom.model.User;
import com.ecommerce.ecom.repository.CartRepo;

@Service
public class CartService {

    @Autowired
    ProductService productService;

    @Autowired
    CartRepo cartRepo;

    public void addToCart(AddToCartDto addToCartDto, User user) throws ProductNotExistException {
        // validate product id
        Product product = productService.findById(addToCartDto.getProductId());

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setProduct(product);
        cart.setQuantity(addToCartDto.getQuantity());
        cart.setCreatedDate(new Date());

        // save to cart
        cartRepo.save(cart);
    }

    public CartDto listCartItems(User user) {
        List<Cart> cartItems = cartRepo.findAllByUserOrderByCreatedDateAsc(user);

        List<CartItemDto> cartItemDtos = new ArrayList<>();
        double totalCost = 0;

        for (Cart cart : cartItems) {
            CartItemDto cartItemDto = new CartItemDto(cart);
            totalCost += cartItemDto.getProductDto().getPrice() * cart.getQuantity();
            cartItemDtos.add(cartItemDto);
        }

        CartDto cartDto = new CartDto();
        cartDto.setTotalCost(totalCost);
        cartDto.setCartItems(cartItemDtos);

        return cartDto;
    }

    public void deleteCartItem(Integer itemId, User user) {
        // check itemId available
        Optional<Cart> optionalCart = cartRepo.findById(itemId);
        if(optionalCart.isEmpty()) {
            throw new CustomException("cart item id does not exists");
        }

        // check itemId belongs to user cart
        Cart cart = optionalCart.get();
        if(cart.getUser() != user) {
            throw new CustomException("invalid user");
        }

        cartRepo.delete(cart);
    }

}
