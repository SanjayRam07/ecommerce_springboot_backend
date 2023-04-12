package com.ecommerce.ecom.dto.cart;

import org.springframework.beans.factory.annotation.Autowired;

import com.ecommerce.ecom.dto.ProductDto;
import com.ecommerce.ecom.model.Cart;
import com.ecommerce.ecom.service.ProductService;

public class CartItemDto {
    
    private Integer Id;
    private Integer quantity;
    private ProductDto productDto;

    public CartItemDto() {
    }

    public CartItemDto(Cart cart) {
        this.Id=cart.getId();
        this.quantity=cart.getQuantity();
        this.productDto=new ProductDto(cart.getProduct());
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public ProductDto getProductDto() {
        return productDto;
    }

    public void setProductDto(ProductDto productDto) {
        this.productDto = productDto;
    }

}
