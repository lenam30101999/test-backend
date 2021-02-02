package com.aibles.reviewservice.repository;

import com.aibles.reviewservice.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    Review save(Review review);

    Optional<Review> findByUid(String uid);

    void deleteByUid(String uid);

    Page<Review> findAllByProductUid(String productUid, Pageable pageable);

    Page<Review> findAllByUserUid(String userUid, Pageable pageable);

    List<Review> findAllByUserUidAndProductUid(String userUid, String productUid);
}
