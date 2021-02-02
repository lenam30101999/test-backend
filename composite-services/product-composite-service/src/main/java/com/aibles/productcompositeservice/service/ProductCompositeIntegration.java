package com.aibles.productcompositeservice.service;

import com.aibles.productcompositeservice.util.ProductEndPoint;
import com.aibles.utils.util.ServiceUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import dtos.ProductDTO;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
@Slf4j
public class ProductCompositeIntegration {
    
    private RestOperations restTemplate;
    private final ServiceUtils util;
    private final ObjectMapper mapper;

    @Autowired
    public ProductCompositeIntegration(RestOperations restTemplate, ServiceUtils util, ObjectMapper mapper) {
        this.restTemplate = restTemplate;
        this.util = util;
        this.mapper = mapper;
    }

    @HystrixCommand(fallbackMethod = "defaultCreateProduct", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")})
    public ResponseEntity<ProductDTO> createProduct(ProductDTO productDTO) {
        String url = ProductEndPoint.URL_PRODUCT_SERVICE;

        ResponseEntity<ProductDTO> result = restTemplate.postForEntity(url, productDTO, ProductDTO.class);

        ProductDTO productDtoResult = result.getBody();
        return util.createOkResponse(productDtoResult);
    }

    /**
     * Fallback method for createProduct()
     *
     * @return
     */
    public ResponseEntity<ProductDTO> defaultCreateProduct(ProductDTO productDTO) {
        log.debug("Using fallback method for product with uid: " + productDTO.getUid());
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

    @HystrixCommand(fallbackMethod = "defaultUpdateProduct", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")})
    public ResponseEntity<ProductDTO> updateProduct(ProductDTO productDTO) {
        String url = ProductEndPoint.URL_PRODUCT_SERVICE;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ProductDTO> requestUpdate = new HttpEntity<>(productDTO ,headers);
        ResponseEntity<ProductDTO> result = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                requestUpdate,
                ProductDTO.class);
        ProductDTO productDtoResult = result.getBody();

        return util.createOkResponse(productDtoResult);
    }

    /**
     * Fallback method for updateProduct()
     *
     * @return
     */
    public ResponseEntity<ProductDTO> defaultUpdateProduct(ProductDTO productDTO) {
        log.debug("Using fallback method for product with uid: " + productDTO.getUid());
        ProductDTO dto = new ProductDTO();
        dto.setProductName("ERROR");

        return util.createResponse(
                dto,
                HttpStatus.OK);
    }

    @HystrixCommand(fallbackMethod = "defaultDeleteProduct", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")})
    public ResponseEntity<String> deleteProduct(String uid) {
        String url = ProductEndPoint.URL_PRODUCT_SERVICE + uid;
        log.debug("deleteProduct from URL: " + url);

        restTemplate.delete(url);
        return util.createOkResponse(HttpStatus.OK.getReasonPhrase());
    }

    /**
     * Fallback method for deleteProduct()
     *
     * @return
     */
    public ResponseEntity<String> defaultDeleteProduct(String uid) {
        log.debug("Using fallback method for product with uid: " + uid );
        return util.createResponse(
                "ERROR",
                HttpStatus.OK);
    }

    @HystrixCommand(fallbackMethod = "defaultGetProduct", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")})
    public ResponseEntity<ProductDTO> getProduct(String uid) {
        String url = ProductEndPoint.URL_PRODUCT_SERVICE + uid;
        ResponseEntity<ProductDTO> result = restTemplate.getForEntity(url, ProductDTO.class);

        ProductDTO productDtoResult = result.getBody();
        log.debug("getProduct: " + productDtoResult.toString());

        return util.createOkResponse(productDtoResult);
    }

    /**
     * Fallback method for getProduct()
     *
     * @return
     */
    public ResponseEntity<ProductDTO> defaultGetProduct(String uid) {
        log.debug("Using fallback method for product with uid = " + uid);
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductName("ERROR");
        return util.createResponse(
                productDTO,
                HttpStatus.OK);
    }

    @SneakyThrows
    @HystrixCommand(fallbackMethod = "defaultGetAllProduct", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")})
    public ResponseEntity<List<ProductDTO>> getAllProduct() {
        log.debug("Will call getAllProduct with hystrix protection");

        String url = ProductEndPoint.URL_PRODUCT_SERVICE;
        log.debug("getAllProduct from URL" + url);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<String> result = restTemplate.exchange(
                builder.build().encode().toUri(),
                HttpMethod.GET,
                entity,
                String.class);

        mapper.registerModule(new JavaTimeModule());
        List<ProductDTO> productDtoResult = Arrays.asList(mapper.readValue(result.getBody(), ProductDTO[].class));

        return util.createOkResponse(productDtoResult);
    }

    /**
     * Fallback method for getAllProduct()
     *
     * @return
     */
    public ResponseEntity<List<ProductDTO>> defaultGetAllProduct() {
        log.debug("Using fallback method for categories");
        List<ProductDTO> productDTOs = new ArrayList<>();
        return util.createResponse(
                productDTOs,
                HttpStatus.OK);
    }

    @SneakyThrows
    @HystrixCommand(fallbackMethod = "defaultGetAllProductByCategoryUid", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")})
    public ResponseEntity<List<ProductDTO>> getAllProductByCategoryUid(String categoryUid) {
        log.debug("Will call getAllProductByCategoryUid with hystrix protection");

        String url = ProductEndPoint.URL_PRODUCT_SERVICE + "?category_uid=" + categoryUid;
        log.debug("getAllProductByCategoryUid from URL" + url);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<String> result = restTemplate.exchange(
                builder.build().encode().toUri(),
                HttpMethod.GET,
                entity,
                String.class);

        mapper.registerModule(new JavaTimeModule());
        List<ProductDTO> productDtoResult = Arrays.asList(mapper.readValue(result.getBody(), ProductDTO[].class));

        return util.createOkResponse(productDtoResult);
    }

    /**
     * Fallback method for getProduct()
     *
     * @return
     */
    public ResponseEntity<List<ProductDTO>> defaultGetAllProductByCategoryUid(String categoryUid) {
        log.debug("Using fallback method for categories = " + categoryUid);
        List<ProductDTO> productDTOs = new ArrayList<>();
        return util.createResponse(
                productDTOs,
                HttpStatus.OK);
    }
}
