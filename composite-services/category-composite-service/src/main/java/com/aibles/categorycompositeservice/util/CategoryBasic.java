package com.aibles.categorycompositeservice.util;

import com.aibles.categorycompositeservice.service.CategoryCompositeIntegration;
import dtos.CategoryDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Log4j2
@Component
public class CategoryBasic {

    @Autowired
    private CategoryCompositeIntegration integration;

    public CategoryDTO createBasicCategory(CategoryDTO categoryDTO) {
        ResponseEntity<CategoryDTO> responseEntity = integration.createCategory(categoryDTO);
        CategoryDTO result = null;

        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            log.debug("Call to addCategory failed: " + responseEntity.getStatusCode());
        } else {
            result = responseEntity.getBody();
        }
        return result;
    }

    public CategoryDTO editBasicCategory(CategoryDTO categoryDTO) {
        ResponseEntity<CategoryDTO> responseEntity = integration.updateCategory(categoryDTO);
        CategoryDTO result = null;

        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            log.debug("Call to updateCategory failed: " + responseEntity.getStatusCode());
        } else {
            result = responseEntity.getBody();
        }
        return result;
    }

    public String deleteBasicCategory(String uid) {
        ResponseEntity<String> responseEntity = integration.deleteCategory(uid);
        String result = null;

        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            log.debug("Call to deleteCategory failed: " + responseEntity.getStatusCode());
        } else {
            result = responseEntity.getBody();
        }
        return result;
    }

    public CategoryDTO getBasicCategory(String uid) {
        ResponseEntity<CategoryDTO> responseEntity = integration.getCategory(uid);
        CategoryDTO dto = null;

        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            log.debug("Call to getCategory failed: " + responseEntity.getStatusCode());
        } else {
            dto = responseEntity.getBody();
        }
        return dto;
    }

    public List<CategoryDTO> getBasicAllCategory() {
        ResponseEntity<List<CategoryDTO>> responseEntity = integration.getAllCategory();
        List<CategoryDTO> dto = null;

        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            log.debug("Call to getCategory failed: " + responseEntity.getStatusCode());
        } else {
            dto = responseEntity.getBody();
        }
        return dto;
    }
}
