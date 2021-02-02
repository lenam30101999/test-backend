package com.aibles.categoryservice.service.impl;

import com.aibles.categoryservice.entity.Category;
import com.aibles.categoryservice.mapper.ModelMapper;
import com.aibles.categoryservice.repository.CategoryRepository;
import com.aibles.categoryservice.service.CategoryService;
import com.aibles.utils.util.Helpers;
import dtos.CategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Caching(
            put = {@CachePut(value = "category", key = "#result.uid")},
            evict = {@CacheEvict(value = "listCategory", allEntries = true)}
    )
    public CategoryDTO saveCategory(CategoryDTO categoryDTO) {
        Category saved = Category.builder()
                                .name(categoryDTO.getName())
                                .description(categoryDTO.getDescription())
                                .uid(Helpers.generateUid())
                                .build();
        saved = categoryRepository.save(saved);
        return modelMapper.convertToDTO(saved);
    }

    @Override
    @Caching(
            evict = {@CacheEvict(value = "listCategory", allEntries = true)},
            cacheable = {@Cacheable(value = "category", key = "#categoryDTO.uid")}
    )
    public CategoryDTO updateCategory(CategoryDTO categoryDTO) {
        Category updated = findByUid(categoryDTO.getUid());
        if (Objects.nonNull(updated)){
            updated.setName(categoryDTO.getName());
            updated.setDescription(categoryDTO.getDescription());

            updated = categoryRepository.saveAndFlush(updated);
            return modelMapper.convertToDTO(updated);
        }else return null;
    }

    @Override
    @Cacheable(value = "category", key = "#uid")
    public CategoryDTO findByUidConvertToDTO(String uid) {
        Category category = findByUid(uid);
        return modelMapper.convertToDTO(category);
    }

    @Override
    @Caching(
            evict = { @CacheEvict(value = "category", key = "#uid"),
                    @CacheEvict(value = "listCategory",allEntries = true)
            }
    )
    public CategoryDTO deleteCategory(String uid) {
        Category deleted = findByUid(uid);
        if (Objects.nonNull(deleted)){
            categoryRepository.deleteByUid(uid);
            return modelMapper.convertToDTO(deleted);
        }else return null;
    }

    @Override
    @Cacheable(value = "listCategory")
    public List<CategoryDTO> findAllCategory() {
        List<Category> categories = categoryRepository.findAll();
        return modelMapper.convertToDTOs(categories);
    }

    private Category findByUid(String uid){
        return categoryRepository.findCategoryByUid(uid).orElse(null);
    }

}
