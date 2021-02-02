package com.aibles.productcompositeservice.util;

import com.aibles.productcompositeservice.service.ProductCompositeIntegration;
import dtos.ProductDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Log4j2
@Component
public class ProductBasic {

    @Autowired
    private ProductCompositeIntegration integration;

    public ProductDTO createBasicProduct(ProductDTO productDTO) {
        ResponseEntity<ProductDTO> responseEntity = integration.createProduct(productDTO);
        ProductDTO result = null;

        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            log.debug("Call to addProduct failed: " + responseEntity.getStatusCode());
        } else {
            result = responseEntity.getBody();
        }
        return result;
    }

    public ProductDTO editBasicProduct(ProductDTO productDTO) {
        ResponseEntity<ProductDTO> responseEntity = integration.updateProduct(productDTO);
        ProductDTO result = null;

        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            log.debug("Call to updateProduct failed: " + responseEntity.getStatusCode());
        } else {
            result = responseEntity.getBody();
        }
        return result;
    }

    public String deleteBasicProduct(String uid) {
        ResponseEntity<String> responseEntity = integration.deleteProduct(uid);
        String result = null;

        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            log.debug("Call to deleteProduct failed: " + responseEntity.getStatusCode());
        } else {
            result = responseEntity.getBody();
        }
        return result;
    }

    public ProductDTO getBasicProduct(String uid) {
        ResponseEntity<ProductDTO> responseEntity = integration.getProduct(uid);
        ProductDTO dto = null;

        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            log.debug("Call to getProduct failed: " + responseEntity.getStatusCode());
        } else {
            dto = responseEntity.getBody();
        }
        return dto;
    }

    public List<ProductDTO> getBasicAllProduct() {
        ResponseEntity<List<ProductDTO>> responseEntity = integration.getAllProduct();
        List<ProductDTO> dto = null;

        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            log.debug("Call to getProduct failed: " + responseEntity.getStatusCode());
        } else {
            dto = responseEntity.getBody();
        }
        return dto;
    }

    public List<ProductDTO> getBasicAllProductByCategoryUid(String categoryUid) {
        ResponseEntity<List<ProductDTO>> responseEntity = integration.getAllProductByCategoryUid(categoryUid);
        List<ProductDTO> dto = null;

        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            log.debug("Call to getProduct failed: " + responseEntity.getStatusCode());
        } else {
            dto = responseEntity.getBody();
        }
        return dto;
    }
}
