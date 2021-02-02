package com.aibles.reviewservice.service.impl;

import com.aibles.reviewservice.entity.Review;
import com.aibles.reviewservice.mapper.ModelMapper;
import com.aibles.reviewservice.repository.ReviewRepository;
import com.aibles.reviewservice.service.ReviewService;
import com.aibles.utils.util.Helpers;
import dtos.ReviewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Caching(
            put = {@CachePut(value = "review", key = "#result.uid")},
            evict = {@CacheEvict(value = "listReviewUser", key = "#reviewDTO.userUid"),
                    @CacheEvict(value = "listReviewProduct", key = "#reviewDTO.productUid")}
    )
    public ReviewDTO createReview(ReviewDTO reviewDTO) {
        Review saved = Review.builder()
                .uid(Helpers.generateUid())
                .rating(reviewDTO.getRating())
                .comment(reviewDTO.getComment())
                .productUid(reviewDTO.getProductUid())
                .userUid(reviewDTO.getUserUid())
                .build();
        saved = reviewRepository.save(saved);
        return modelMapper.convertToDTO(saved);
    }

    @Override
    @Caching(
            evict = {@CacheEvict(value = "listReviewUser", key = "#reviewDTO.userUid"),
                    @CacheEvict(value = "listReviewProduct", key = "#reviewDTO.productUid"),
                    @CacheEvict(value = "review", key = "#reviewDTO.uid")},
            put = {@CachePut(value = "review", key = "#result.uid")}
    )
    public ReviewDTO updateReview(ReviewDTO reviewDTO) {
        Review updated = findByUid(reviewDTO.getUid());
        if (Objects.nonNull(updated)){
            updated.setRating(reviewDTO.getRating());
            updated.setComment(reviewDTO.getComment());

            updated = reviewRepository.saveAndFlush(updated);
            return modelMapper.convertToDTO(updated);
        }else return null;
    }

    @Override
    @Cacheable(value = "review", key = "#uid")
    public ReviewDTO findByUidConvertToDTO(String uid) {
        Review review = findByUid(uid);
        return modelMapper.convertToDTO(review);
    }

    @Override
    @Caching(
            evict = {@CacheEvict(value = "review", key = "#uid"),
                    @CacheEvict(value = "listReviewUser", key = "#result.userUid"),
                    @CacheEvict(value = "listReviewProduct", key = "#result.productUid")}
    )
    public ReviewDTO deleteByUid(String uid) {
        Review deleted = findByUid(uid);
        if (Objects.nonNull(deleted)){
            reviewRepository.deleteByUid(uid);
            return modelMapper.convertToDTO(deleted);
        }else return null;
    }

    @Override
    @Cacheable(value = "listReviewProduct", key = "#productUid + #pageable.pageNumber")
    public List<ReviewDTO> findAllByProductUid(String productUid, Pageable pageable) {
        Page<Review> reviewPage = reviewRepository.findAllByProductUid(productUid, pageable);
        List<Review> reviewList = reviewPage.getContent();
        return modelMapper.convertToDTOList(reviewList);
    }

    @Override
    @Cacheable(value = "listReviewUser", key = "#userUid + #pageable.pageNumber")
    public List<ReviewDTO> findAllByUserUid(String userUid, Pageable pageable) {
        Page<Review> reviewPage = reviewRepository.findAllByUserUid(userUid, pageable);
        List<Review> reviewList = reviewPage.getContent();
        return modelMapper.convertToDTOList(reviewList);
    }

    @Override
    @Cacheable(value = "listReview", key = "#userUid + #productUid")
    public List<ReviewDTO> findAllByUserUidAndProductUid(String userUid, String productUid) {
        List<Review> reviews = reviewRepository.findAllByUserUidAndProductUid(userUid, productUid);
        return modelMapper.convertToDTOList(reviews);
    }

    private Review findByUid(String uid){
        return reviewRepository.findByUid(uid).orElse(null);
    }

}
