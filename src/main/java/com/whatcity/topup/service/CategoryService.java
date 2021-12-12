package com.whatcity.topup.service;

import com.whatcity.topup.model.table.AdminNews;
import com.whatcity.topup.model.table.Category;
import com.whatcity.topup.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findCategoryAll() {
        return categoryRepository.findAll();
    }

    public Category findCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }
}
