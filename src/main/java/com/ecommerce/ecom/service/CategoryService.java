package com.ecommerce.ecom.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.ecom.model.Category;
import com.ecommerce.ecom.repository.CategoryRepo;

@Service
public class CategoryService {
    
    @Autowired
    CategoryRepo categoryRepo;

    public void createCategory(Category category) {
        categoryRepo.save(category);
    }

    public List<Category> listCategory() {
        return categoryRepo.findAll();
    }

    public void updateCategory(int categoryId, Category newCategory) {
        Category currentCategory=categoryRepo.getById(categoryId);
        currentCategory.setCategoryName(newCategory.getCategoryName());
        currentCategory.setDescription(newCategory.getDescription());
        currentCategory.setImageUrl(newCategory.getImageUrl());
        categoryRepo.save(currentCategory);
    }

    public boolean findById(int categoryId) {
        return categoryRepo.findById(categoryId).isPresent();
    }
}
