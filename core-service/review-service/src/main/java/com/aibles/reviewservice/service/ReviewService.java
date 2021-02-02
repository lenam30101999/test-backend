package com.aibles.reviewservice.service;

import dtos.ReviewDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewService {
    ReviewDTO createReview(ReviewDTO reviewDTO);

    ReviewDTO updateReview(ReviewDTO reviewDTO);

    ReviewDTO findByUidConvertToDTO(String uid);

    ReviewDTO deleteByUid(String uid);

    List<ReviewDTO> findAllByProductUid(String productUid, Pageable pageable);

    List<ReviewDTO> findAllByUserUid(String userUid, Pageable pageable);

    List<ReviewDTO> findAllByUserUidAndProductUid(String userUid, String productUid);
}
