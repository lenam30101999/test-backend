package com.aibles.productservice.controller;

import com.aibles.productservice.service.ProductService;
import dtos.ProductDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Log4j2
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    
    @Autowired
    private ProductService productService;

    @PostMapping(produces = "application/json")
    public ResponseEntity<?> addProduct(@RequestBody @Valid ProductDTO productDTO){
        ProductDTO saved = productService.saveProduct(productDTO);

        try{
            log.info("create: {}", saved);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        }catch (NullPointerException e){
            log.info("error: ", e);
            return new ResponseEntity<>(HttpStatus.SEE_OTHER.getReasonPhrase(), HttpStatus.SEE_OTHER);
        }
    }

    @PutMapping(produces = "application/json")
    public ResponseEntity<?> editProduct(@Valid @RequestBody ProductDTO productDTO){
        ProductDTO updated = productService.editProduct(productDTO);

        try{
            log.info("edit: {}", updated);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }catch (NullPointerException e){
            log.info("error: ", e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{uid}", produces = "application/json")
    public ResponseEntity<?> deleteProduct(@PathVariable("uid") String uid){
        ProductDTO deleted = productService.deleteByUid(uid);

        if (Objects.nonNull(deleted)){
            log.info("delete successfully");
            return new ResponseEntity<>(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK);
        }else{
            log.info("Not successfully");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/{uid}", produces = "application/json")
    public ResponseEntity<?> findProductByUid(@PathVariable("uid") String uid){
        ProductDTO productDTO = productService.findProductByUid(uid);

        if (Objects.nonNull(productDTO)){
            log.info("find: {}", productDTO);
            return new ResponseEntity<>(productDTO, HttpStatus.OK);
        }else{
            log.info("Not successfully");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(params = "category_uid", produces = "application/json")
    public ResponseEntity<?> findProductByCategoryUid(@RequestParam("category_uid") String categoryUid,
                                                      Pageable pageable){
        List<ProductDTO> productDTOs = productService.findAllByCategoryUid(categoryUid, pageable);

        if (Objects.nonNull(productDTOs)){
            log.info("find: {}", productDTOs);
            return new ResponseEntity<>(productDTOs, HttpStatus.OK);
        }else{
            log.info("Not successfully");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND.getReasonPhrase(),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<?> findAllProduct(Pageable pageable){
        List<ProductDTO> productDTOs = productService.findAll(pageable);

        if (productDTOs != null){
            log.info("find: {}", productDTOs);
            return new ResponseEntity<>(productDTOs, HttpStatus.OK);
        }else{
            log.info("Not successfully");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND.getReasonPhrase(),HttpStatus.NOT_FOUND);
        }
    }
}
