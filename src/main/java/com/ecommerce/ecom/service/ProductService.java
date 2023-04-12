package com.ecommerce.ecom.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.ecom.dto.ProductDto;
import com.ecommerce.ecom.exceptions.ProductNotExistException;
import com.ecommerce.ecom.model.Category;
import com.ecommerce.ecom.model.Product;
import com.ecommerce.ecom.repository.ProductRepo;

@Service
public class ProductService {
    
    @Autowired
    ProductRepo productRepo;

    public void createProduct(ProductDto productDto, Category category) {
        Product product=new Product();

        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setCategory(category);
        product.setPrice(productDto.getPrice());
        product.setImageUrl(productDto.getImageUrl());

        productRepo.save(product);
    }

    public ProductDto getProductDto(Product product) {
        ProductDto productDto=new ProductDto();

        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setCategoryId(product.getCategory().getId());
        productDto.setImageUrl(product.getImageUrl());
        productDto.setPrice(product.getPrice());

        return productDto;
    }

    public List<ProductDto> getAllProducts() {
        List<Product> products=productRepo.findAll();
        List<ProductDto> productDtos=new ArrayList<>();

        for(Product product:products) {
            productDtos.add(getProductDto(product));
        }

        return productDtos;
    }

    public void updateProduct(ProductDto productDto, Integer productId) throws Exception {
        Optional<Product> optionalProduct=productRepo.findById(productId);
        if(!optionalProduct.isPresent()) {
            throw new Exception("product not exists");
        }

        Product product = optionalProduct.get();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setImageUrl(productDto.getImageUrl());
        product.setPrice(productDto.getPrice());

        productRepo.save(product);
    }

    public Product findById(Integer productId) throws ProductNotExistException {
        Optional<Product> optionalProduct = productRepo.findById(productId);
        if(optionalProduct.isEmpty()) {
            throw new ProductNotExistException("product id is invalid " + productId);
        }

        return optionalProduct.get();

    }
}
