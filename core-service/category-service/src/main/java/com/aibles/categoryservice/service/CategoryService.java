package com.aibles.categoryservice.service;

import dtos.CategoryDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    CategoryDTO saveCategory(CategoryDTO categoryDTO);

    CategoryDTO updateCategory(CategoryDTO categoryDTO);

    CategoryDTO findByUidConvertToDTO(String uid);

    CategoryDTO deleteCategory(String uid);

    List<CategoryDTO> findAllCategory();
}
