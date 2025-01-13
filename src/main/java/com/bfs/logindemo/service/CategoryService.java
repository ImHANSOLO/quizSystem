package com.bfs.logindemo.service;

import com.bfs.logindemo.entity.Category;
import com.bfs.logindemo.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepo;

    public CategoryService(CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    public List<Category> findAll() {
        return categoryRepo.findAll();
    }

    public Category findById(int categoryId) {
        return categoryRepo.findById(categoryId).orElse(null);
    }

    public void createCategory(String name) {
        Category c = new Category();
        c.setName(name);
        categoryRepo.save(c);
    }

    public void updateCategory(int categoryId, String newName) {
        categoryRepo.updateName(categoryId, newName);
    }

    public void deleteCategory(int categoryId) {
        categoryRepo.deleteById(categoryId);
    }
}
