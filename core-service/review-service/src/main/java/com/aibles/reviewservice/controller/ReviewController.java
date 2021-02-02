package com.aibles.reviewservice.controller;

import com.aibles.reviewservice.service.ReviewService;
import dtos.ReviewDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("Duplicates")
@Log4j2
@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @PostMapping(produces = "application/json")
    public ResponseEntity<?> createReview(@RequestBody @Valid ReviewDTO reviewDTO){
        ReviewDTO saved = reviewService.createReview(reviewDTO);
        try{
            log.info("create: {}", saved);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        }catch (NullPointerException e){
            log.info("error: ", e);
            return new ResponseEntity<>(HttpStatus.SEE_OTHER.getReasonPhrase(), HttpStatus.SEE_OTHER);
        }
    }

    @PutMapping(produces = "application/json")
    public ResponseEntity<?> editReview(@Valid @RequestBody ReviewDTO reviewDTO){
        ReviewDTO updated = reviewService.updateReview(reviewDTO);
        try{
            log.info("edit: {}", updated);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }catch (NullPointerException e){
            log.info("error: ", e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{uid}", produces = "application/json")
    public ResponseEntity<?> deleteReview(@PathVariable("uid") String uid){
        ReviewDTO deleted = reviewService.deleteByUid(uid);

        if (Objects.nonNull(deleted)){
            log.info("delete successfully");
            return new ResponseEntity<>(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK);
        }else{
            log.info("Not successfully");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/{uid}", produces = "application/json")
    public ResponseEntity<?> findReviewByUid(@PathVariable("uid") String uid){
        ReviewDTO reviewDTO = reviewService.findByUidConvertToDTO(uid);

        try {
            log.info("find: {}", reviewDTO);
            return new ResponseEntity<>(reviewDTO, HttpStatus.OK);
        } catch (NullPointerException e){
            log.info("error: " + e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(params = "user_uid", produces = "application/json")
    public ResponseEntity<?> findReviewByUserUid(@RequestParam("user_uid") String userUid, Pageable pageable){
        List<ReviewDTO> reviewDTO = reviewService.findAllByUserUid(userUid, pageable);

        if (Objects.nonNull(reviewDTO)){
            log.info("find: {}", reviewDTO);
            return new ResponseEntity<>(reviewDTO, HttpStatus.OK);
        }else{
            log.info("Not successfully");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND.getReasonPhrase(),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(params = "product_uid", produces = "application/json")
    public ResponseEntity<?> findReviewByProductUid(@RequestParam("product_uid") String productUid, Pageable pageable){
        List<ReviewDTO> reviewDTO = reviewService.findAllByProductUid(productUid, pageable);

        if (Objects.nonNull(reviewDTO)){
            log.info("find: {}", reviewDTO);
            return new ResponseEntity<>(reviewDTO, HttpStatus.OK);
        }else{
            log.info("Not successfully");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND.getReasonPhrase(),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<?> findByUserUidAndProductUid(@RequestParam("user_uid") String userUid,
                                                        @RequestParam("product_uid") String productUid){
        List<ReviewDTO> reviewDTOs = reviewService.findAllByUserUidAndProductUid(userUid, productUid);

        if (reviewDTOs != null){
            log.info("find: {}", reviewDTOs);
            return new ResponseEntity<>(reviewDTOs, HttpStatus.OK);
        }else{
            log.info("Not successfully");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND.getReasonPhrase(),HttpStatus.NOT_FOUND);
        }
    }
}
