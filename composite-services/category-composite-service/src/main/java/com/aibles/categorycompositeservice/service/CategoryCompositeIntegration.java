package com.aibles.categorycompositeservice.service;

import com.aibles.categorycompositeservice.util.CategoryEndPoint;
import com.aibles.utils.util.ServiceUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import dtos.CategoryDTO;
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
public class CategoryCompositeIntegration {
    
    private RestOperations restTemplate;
    private final ServiceUtils util;
    private final ObjectMapper mapper;

    @Autowired
    public CategoryCompositeIntegration(RestOperations restTemplate, ServiceUtils util, ObjectMapper mapper) {
        this.restTemplate = restTemplate;
        this.util = util;
        this.mapper = mapper;
    }

    @HystrixCommand(fallbackMethod = "defaultCreateCategory", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")})
    public ResponseEntity<CategoryDTO> createCategory(CategoryDTO categoryDTO) {
        String url = CategoryEndPoint.URL_CATEGORY_SERVICE;

        ResponseEntity<CategoryDTO> result = restTemplate.postForEntity(url, categoryDTO, CategoryDTO.class);

        CategoryDTO categoryDtoResult = result.getBody();
        return util.createOkResponse(categoryDtoResult);
    }

    /**
     * Fallback method for createCategory()
     *
     * @return
     */
    public ResponseEntity<CategoryDTO> defaultCreateCategory(CategoryDTO categoryDTO) {
        log.debug("Using fallback method for category with uid: " + categoryDTO.getUid());
        return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
    }

    @HystrixCommand(fallbackMethod = "defaultUpdateCategory", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")})
    public ResponseEntity<CategoryDTO> updateCategory(CategoryDTO categoryDTO) {
        String url = CategoryEndPoint.URL_CATEGORY_SERVICE;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CategoryDTO> requestUpdate = new HttpEntity<>(categoryDTO ,headers);
        ResponseEntity<CategoryDTO> result = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                requestUpdate,
                CategoryDTO.class);
        CategoryDTO categoryDtoResult = result.getBody();

        return util.createOkResponse(categoryDtoResult);
    }

    /**
     * Fallback method for updateCategory()
     *
     * @return
     */
    public ResponseEntity<CategoryDTO> defaultUpdateCategory(CategoryDTO categoryDTO) {
        log.debug("Using fallback method for category with uid: " + categoryDTO.getUid());
        CategoryDTO dto = new CategoryDTO();
        dto.setName("ERROR");

        return util.createResponse(
                dto,
                HttpStatus.OK);
    }

    @HystrixCommand(fallbackMethod = "defaultDeleteCategory", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")})
    public ResponseEntity<String> deleteCategory(String uid) {
        String url = CategoryEndPoint.URL_CATEGORY_SERVICE + uid;
        log.debug("deleteCategory from URL: " + url);

        restTemplate.delete(url);
        return util.createOkResponse(HttpStatus.OK.getReasonPhrase());
    }

    /**
     * Fallback method for deleteCategory()
     *
     * @return
     */
    public ResponseEntity<String> defaultDeleteCategory(String uid) {
        log.debug("Using fallback method for category with uid: " + uid );
        return util.createResponse(
                "ERROR",
                HttpStatus.OK);
    }

    @HystrixCommand(fallbackMethod = "defaultGetCategory", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")})
    public ResponseEntity<CategoryDTO> getCategory(String uid) {
        String url = CategoryEndPoint.URL_CATEGORY_SERVICE + uid;
        ResponseEntity<CategoryDTO> result = restTemplate.getForEntity(url, CategoryDTO.class);

        CategoryDTO categoryDtoResult = result.getBody();
        log.debug("getCategory: " + categoryDtoResult.toString());

        return util.createOkResponse(categoryDtoResult);
    }

    /**
     * Fallback method for getCategory()
     *
     * @return
     */
    public ResponseEntity<CategoryDTO> defaultGetCategory(String uid) {
        log.debug("Using fallback method for category with uid = " + uid);
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("ERROR");
        return util.createResponse(
                categoryDTO,
                HttpStatus.OK);
    }

    @SneakyThrows
    @HystrixCommand(fallbackMethod = "defaultGetAllCategory", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")})
    public ResponseEntity<List<CategoryDTO>> getAllCategory() {
        log.debug("Will call getAllCategory with hystrix protection");

        String url = CategoryEndPoint.URL_CATEGORY_SERVICE;
        log.debug("getAllCategory from URL" + url);

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
        List<CategoryDTO> categoryDtoResult = Arrays.asList(mapper.readValue(result.getBody(), CategoryDTO[].class));

        return util.createOkResponse(categoryDtoResult);
    }

    /**
     * Fallback method for getCategory()
     *
     * @return
     */
    public ResponseEntity<List<CategoryDTO>> defaultGetAllCategory() {
        log.debug("Using fallback method for categories");
        List<CategoryDTO> categoryDTOs = new ArrayList<>();
        return util.createResponse(
                categoryDTOs,
                HttpStatus.OK);
    }
}
