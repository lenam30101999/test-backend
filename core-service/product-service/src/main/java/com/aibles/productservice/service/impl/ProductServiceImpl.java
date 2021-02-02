package com.aibles.productservice.service.impl;

import com.aibles.productservice.entity.Product;
import com.aibles.productservice.mapper.ModelMapper;
import com.aibles.productservice.repository.ProductRepository;
import com.aibles.productservice.service.ProductService;
import com.aibles.utils.util.Helpers;
import dtos.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Caching(
            put = {@CachePut(value = "product", key = "#result.uid")},
            evict = {@CacheEvict(value = "listProduct", key = "#productDTO.categoryUid")}
    )
    public ProductDTO saveProduct(ProductDTO productDTO) {
        Product saved = Product.builder()
                .productName(productDTO.getProductName())
                .description(productDTO.getDescription())
                .publisherName(productDTO.getPublisherName())
                .price(productDTO.getPrice())
                .imageUrl(productDTO.getImageUrl())
                .categoryUid(productDTO.getCategoryUid())
                .uid(Helpers.generateUid())
                .build();
        saved = productRepository.save(saved);
        return modelMapper.convertToDTO(saved);
    }

    @Override
    @Caching(
            evict = {@CacheEvict(value = "listProduct", key = "#productDTO.categoryUid"),
                    @CacheEvict(value = "product", key = "#productDTO.uid")},
            cacheable = {@Cacheable(value = "product", key = "#productDTO.uid")}
    )
    public ProductDTO editProduct(ProductDTO productDTO) {
        Product updated = findByUid(productDTO.getUid());
        if (Objects.nonNull(updated)){
            updated.setProductName(productDTO.getProductName());
            updated.setImageUrl(productDTO.getImageUrl());
            updated.setDescription(productDTO.getDescription());
            updated.setPublisherName(productDTO.getPublisherName());
            updated.setPrice(productDTO.getPrice());

            updated = productRepository.saveAndFlush(updated);
            return modelMapper.convertToDTO(updated);
        }else return null;
    }

    @Override
    @Cacheable(value = "product", key = "#uid")
    public ProductDTO findProductByUid(String uid) {
        Product product = findByUid(uid);
        return modelMapper.convertToDTO(product);
    }

    @Override
    @Caching(
            evict = { @CacheEvict(value = "product", key = "#uid"),
                    @CacheEvict(value = "listProduct", allEntries = true)
            }
    )
    public ProductDTO deleteByUid(String uid) {
        Product deleted = findByUid(uid);
        if (Objects.nonNull(deleted)){
            productRepository.deleteByUid(uid);
            return modelMapper.convertToDTO(deleted);
        }else return null;
    }

    @Override
    @Cacheable(value = "listProduct", key = "#categoryUid + #pageable.pageNumber")
    public List<ProductDTO> findAllByCategoryUid(String categoryUid, Pageable pageable) {
        Page<Product> productPage = productRepository.findAllByCategoryUid(categoryUid, pageable);
        List<Product> productList = productPage.getContent();
        return modelMapper.convertToDTOs(productList);
    }

    @Override
    @Cacheable(value = "listProduct", key = "#pageable.pageNumber")
    public List<ProductDTO> findAll(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        List<Product> productList = productPage.getContent();
        return modelMapper.convertToDTOs(productList);
    }

    private Product findByUid(String uid){
        return productRepository.findProductByUid(uid).orElse(null);
    }

}
