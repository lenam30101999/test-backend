package com.aibles.reviewcompositeservice.service;

import com.aibles.reviewcompositeservice.kafka.producer.ReviewProducer;
import com.aibles.reviewcompositeservice.util.ReviewBasic;
import com.aibles.utils.util.ServiceUtils;
import dtos.ReviewDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import java.util.List;
import java.util.Objects;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@RestController
@RequestMapping("/api/v1/reviews")
@Slf4j
public class ReviewCompositeService {

    private final ReviewProducer reviewProducer;
    private final ReviewBasic reviewBasic;
    private final ServiceUtils util;

    @Autowired
    public ReviewCompositeService(ReviewProducer reviewProducer, ReviewBasic reviewBasic, ServiceUtils util) {
        this.reviewProducer = reviewProducer;
        this.reviewBasic = reviewBasic;
        this.util = util;
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<?> addReview(@RequestBody @Valid ReviewDTO reviewDTO){
        if (Objects.nonNull(reviewDTO)){
            ReviewDTO dto = reviewBasic.createBasicReview(reviewDTO);
            return util.createOkResponse(dto);
        }else {
            return util.createResponse(HttpStatus.SEE_OTHER.getReasonPhrase(), HttpStatus.SEE_OTHER);
        }
    }

    @PutMapping(produces = "application/json")
    public ResponseEntity<?> editReview(@Valid @RequestBody ReviewDTO reviewDTO){
        if (Objects.nonNull(reviewDTO)){
            ReviewDTO dto = reviewBasic.editBasicReview(reviewDTO);
            return util.createOkResponse(dto);
        }else {
            return util.createResponse(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{uid}", produces = "application/json")
    public ResponseEntity<?> deleteReview(@PathVariable("uid") String uid){
        String result = reviewProducer.send(uid);
        if (Objects.nonNull(result)){
            return util.createOkResponse(result);
        }else {
            return util.createResponse(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/{uid}", produces = "application/json")
    public ResponseEntity<?> findReviewById(@PathVariable("uid") String uid){
        ReviewDTO result = reviewBasic.getBasicReview(uid);

        if (Objects.nonNull(result)){
            return util.createOkResponse(result);
        }else {
            return util.createResponse(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(params = "user_uid", produces = "application/json")
    public ResponseEntity<?> findAllReviewByUserUid(@RequestParam("user_uid") String userUid){
        List<ReviewDTO> result = reviewBasic.getBasicAllReviewByUserUid(userUid);

        if (Objects.nonNull(result)){
            return util.createOkResponse(result);
        }else {
            return util.createResponse(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(params = "product_uid", produces = "application/json")
    public ResponseEntity<?> findAllReviewByProductUid(@RequestParam("product_uid") String productUid){
        List<ReviewDTO> result = reviewBasic.getBasicAllReviewByProductUid(productUid);

        if (Objects.nonNull(result)){
            return util.createOkResponse(result);
        }else {
            return util.createResponse(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<?> findReviewByUserUidAndProductUid(@RequestParam("user_uid") String userUid,
                                                              @RequestParam("product_uid") String productUid){
        List<ReviewDTO> result = reviewBasic.getBasicReviewByUserUidAndProductUid(userUid, productUid);

        if (Objects.nonNull(result)){
            return util.createOkResponse(result);
        }else {
            return util.createResponse(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
        }
    }
}
