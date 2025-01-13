package com.bfs.logindemo.repository;

import com.bfs.logindemo.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Transactional
    @Modifying
    @Query("UPDATE Category c SET c.name = :newName WHERE c.categoryId = :categoryId")
    int updateName(int categoryId, String newName);
}
