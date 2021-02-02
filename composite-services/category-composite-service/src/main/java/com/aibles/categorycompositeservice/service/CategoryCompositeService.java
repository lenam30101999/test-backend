package com.aibles.categorycompositeservice.service;

import com.aibles.categorycompositeservice.util.CategoryBasic;
import com.aibles.categorycompositeservice.kafka.producer.CategoryProducer;
import com.aibles.utils.util.ServiceUtils;
import dtos.CategoryDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import java.util.List;
import java.util.Objects;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@RestController
@RequestMapping("/api/v1/categories")
@Slf4j
public class CategoryCompositeService {

    private final CategoryProducer categoryProducer;
    private final CategoryBasic categoryBasic;
    private final ServiceUtils util;

    @Autowired
    public CategoryCompositeService(CategoryProducer categoryProducer, CategoryBasic categoryBasic, ServiceUtils util) {
        this.categoryProducer = categoryProducer;
        this.categoryBasic = categoryBasic;
        this.util = util;
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<?> addCategory(@RequestBody @Valid CategoryDTO categoryDTO){
        if (Objects.nonNull(categoryDTO)){
            CategoryDTO dto = categoryBasic.createBasicCategory(categoryDTO);
            return util.createOkResponse(dto);
        }else {
            return util.createResponse(HttpStatus.SEE_OTHER.getReasonPhrase(), HttpStatus.SEE_OTHER);
        }
    }

    @PutMapping(produces = "application/json")
    public ResponseEntity<?> editCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        if (Objects.nonNull(categoryDTO)){
            CategoryDTO dto = categoryBasic.editBasicCategory(categoryDTO);
            return util.createOkResponse(dto);
        }else {
            return util.createResponse(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{uid}", produces = "application/json")
    public ResponseEntity<?> deleteCategory(@PathVariable("uid") String uid){
        String result = categoryProducer.send(uid);
        if (Objects.nonNull(result)){
            return util.createOkResponse(result);
        }else {
            return util.createResponse(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/{uid}", produces = "application/json")
    public ResponseEntity<?> findCategoryById(@PathVariable("uid") String uid){
        CategoryDTO result = categoryBasic.getBasicCategory(uid);

        if (Objects.nonNull(result)){
            return util.createOkResponse(result);
        }else {
            return util.createResponse(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<?> findAllCategory(){
        List<CategoryDTO> result = categoryBasic.getBasicAllCategory();

        if (Objects.nonNull(result)){
            return util.createOkResponse(result);
        }else {
            return util.createResponse(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
        }
    }
}
