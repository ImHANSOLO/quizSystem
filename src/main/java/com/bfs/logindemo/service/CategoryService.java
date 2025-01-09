package com.bfs.logindemo.service;

import com.bfs.logindemo.dao.CategoryDao;
import com.bfs.logindemo.domain.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryDao categoryDao;

    public CategoryService(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    public List<Category> findAll() {
        return categoryDao.findAll();
    }

    public Category findById(int categoryId) {
        return categoryDao.findById(categoryId);
    }

    public void createCategory(String name) {
        Category c = new Category();
        c.setName(name);
        categoryDao.insert(c);
    }

    public void updateCategory(int categoryId, String newName) {
        categoryDao.updateName(categoryId, newName);
    }

    public void deleteCategory(int categoryId) {
        categoryDao.delete(categoryId);
    }
}