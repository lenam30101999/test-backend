package com.aibles.productcompositeservice.service;

import com.aibles.productcompositeservice.kafka.producer.ProductProducer;
import com.aibles.productcompositeservice.util.ProductBasic;
import com.aibles.utils.util.ServiceUtils;
import dtos.ProductDTO;
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
@RequestMapping("/api/v1/products")
@Slf4j
public class ProductCompositeService {

    private final ProductProducer productProducer;
    private final ProductBasic productBasic;
    private final ServiceUtils util;

    @Autowired
    public ProductCompositeService(ProductProducer productProducer, ProductBasic productBasic, ServiceUtils util) {
        this.productProducer = productProducer;
        this.productBasic = productBasic;
        this.util = util;
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<?> addProduct(@RequestBody @Valid ProductDTO productDTO){
        if (Objects.nonNull(productDTO)){
            ProductDTO dto = productBasic.createBasicProduct(productDTO);
            return util.createOkResponse(dto);
        }else {
            return util.createResponse(HttpStatus.SEE_OTHER.getReasonPhrase(), HttpStatus.SEE_OTHER);
        }
    }

    @PutMapping(produces = "application/json")
    public ResponseEntity<?> editProduct(@Valid @RequestBody ProductDTO productDTO){
        if (Objects.nonNull(productDTO)){
            ProductDTO dto = productBasic.editBasicProduct(productDTO);
            return util.createOkResponse(dto);
        }else {
            return util.createResponse(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{uid}", produces = "application/json")
    public ResponseEntity<?> deleteProduct(@PathVariable("uid") String uid){
        String result = productProducer.send(uid);
        if (Objects.nonNull(result)){
            return util.createOkResponse(result);
        }else {
            return util.createResponse(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/{uid}", produces = "application/json")
    public ResponseEntity<?> findProductById(@PathVariable("uid") String uid){
        ProductDTO result = productBasic.getBasicProduct(uid);

        if (Objects.nonNull(result)){
            return util.createOkResponse(result);
        }else {
            return util.createResponse(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<?> findAllProduct(){
        List<ProductDTO> result = productBasic.getBasicAllProduct();

        if (Objects.nonNull(result)){
            return util.createOkResponse(result);
        }else {
            return util.createResponse(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(params = "category_uid", produces = "application/json")
    public ResponseEntity<?> findAllProductByProductUid(@RequestParam("category_uid") String reviewUid){
        List<ProductDTO> result = productBasic.getBasicAllProductByCategoryUid(reviewUid);

        if (Objects.nonNull(result)){
            return util.createOkResponse(result);
        }else {
            return util.createResponse(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
        }
    }
}
