package com.ecommerce.ecom.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecom.common.ApiResponse;
import com.ecommerce.ecom.dto.ProductDto;
import com.ecommerce.ecom.model.Category;
import com.ecommerce.ecom.model.Product;
import com.ecommerce.ecom.repository.CategoryRepo;
import com.ecommerce.ecom.repository.ProductRepo;
import com.ecommerce.ecom.service.ProductService;

import springfox.documentation.swagger.web.ApiResourceController;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    ProductRepo productRepo;

    @PostMapping("/addProduct")
    public ResponseEntity<ApiResponse> createProduct(@RequestBody ProductDto productDto) {
        Optional<Category> optionalCategory = categoryRepo.findById(productDto.getCategoryId());
        if(!optionalCategory.isPresent()) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "category id not exists"),HttpStatus.NOT_FOUND);
        }
        productService.createProduct(productDto,optionalCategory.get());
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "product added"),HttpStatus.CREATED);
    }

    @GetMapping("listProduct")
    public ResponseEntity<List<ProductDto>> getProducts() {
        List<ProductDto> productDtos=productService.getAllProducts();
        return new ResponseEntity<>(productDtos,HttpStatus.OK);
    }

    @PostMapping("/updateProduct/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable("productId") Integer productId, @RequestBody ProductDto productDto) throws Exception {
        Optional<Category> optionalCategory = categoryRepo.findById(productDto.getCategoryId());
        if(!optionalCategory.isPresent()) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "category id not exists"),HttpStatus.NOT_FOUND);
        }
        productService.updateProduct(productDto,productId);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "product updated"),HttpStatus.OK);
    }

}
