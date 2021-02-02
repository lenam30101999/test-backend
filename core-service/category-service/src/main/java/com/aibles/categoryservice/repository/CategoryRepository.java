package com.aibles.categoryservice.repository;

import com.aibles.categoryservice.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category save(Category category);

    Category findCategoryById(int id);

    Optional<Category> findCategoryByUid(String uid);

    void deleteByUid(String uid);

    List<Category> findAll();
}
