package com.aibles.productservice.service;

import dtos.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProductService {
    ProductDTO saveProduct(ProductDTO productDTO);

    ProductDTO editProduct(ProductDTO productDTO);

    ProductDTO findProductByUid(String uid);

    ProductDTO deleteByUid(String uid);

    List<ProductDTO> findAllByCategoryUid(String categoryUid, Pageable pageable);

    List<ProductDTO> findAll(Pageable pageable);
}
