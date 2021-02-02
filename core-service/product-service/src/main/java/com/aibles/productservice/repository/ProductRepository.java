package com.aibles.productservice.repository;

import com.aibles.productservice.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product save(Product product);

    Optional<Product> findProductByUid(String uid);

    void deleteByUid(String uid);

    Page<Product> findAllByCategoryUid(String uid, Pageable pageable);

    Page<Product> findAll(Pageable pageable);
}
