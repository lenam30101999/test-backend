package com.aibles.categoryservice.controller;

import com.aibles.categoryservice.service.CategoryService;
import dtos.CategoryDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Log4j2
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping(produces = "application/json")
    public ResponseEntity<?> addCategory(@RequestBody @Valid CategoryDTO categoryDTO){
        CategoryDTO saved = categoryService.saveCategory(categoryDTO);

        try{
            log.info("create: {}", saved);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        }catch (NullPointerException e){
            log.info("error: ", e);
            return new ResponseEntity<>(HttpStatus.SEE_OTHER.getReasonPhrase(), HttpStatus.SEE_OTHER);
        }
    }

    @PutMapping(produces = "application/json")
    public ResponseEntity<?> editCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        CategoryDTO updated = categoryService.updateCategory(categoryDTO);

        try{
            log.info("edit: {}", updated);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }catch (NullPointerException e){
            log.info("error: ", e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{uid}", produces = "application/json")
    public ResponseEntity<?> deleteCategory(@PathVariable("uid") String uid){
        CategoryDTO deleted = categoryService.deleteCategory(uid);

        if (Objects.nonNull(deleted)){
            log.info("delete successfully");
            return new ResponseEntity<>(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK);
        }else{
            log.info("Not successfully");
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/{uid}", produces = "application/json")
    public ResponseEntity<?> findCategoryById(@PathVariable("uid") String uid){
        CategoryDTO categoryDTO = categoryService.findByUidConvertToDTO(uid);

        if (Objects.nonNull(categoryDTO)){
            log.info("find: {}", categoryDTO);
            return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
        }else{
            log.info("Not successfully");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND.getReasonPhrase(),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<?> findAllCategory(){
        List<CategoryDTO> categoryDTOs = categoryService.findAllCategory();

        if (categoryDTOs != null){
            log.info("find: {}", categoryDTOs);
            return new ResponseEntity<>(categoryDTOs, HttpStatus.OK);
        }else{
            log.info("Not successfully");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND.getReasonPhrase(),HttpStatus.NOT_FOUND);
        }
    }

}
