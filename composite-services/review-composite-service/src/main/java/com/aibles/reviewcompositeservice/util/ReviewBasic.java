package com.aibles.reviewcompositeservice.util;

import com.aibles.reviewcompositeservice.service.ReviewCompositeIntegration;
import dtos.ReviewDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Log4j2
@Component
public class ReviewBasic {

    @Autowired
    private ReviewCompositeIntegration integration;

    public ReviewDTO createBasicReview(ReviewDTO reviewDTO) {
        ResponseEntity<ReviewDTO> responseEntity = integration.createReview(reviewDTO);
        ReviewDTO result = null;

        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            log.debug("Call to addReview failed: " + responseEntity.getStatusCode());
        } else {
            result = responseEntity.getBody();
        }
        return result;
    }

    public ReviewDTO editBasicReview(ReviewDTO reviewDTO) {
        ResponseEntity<ReviewDTO> responseEntity = integration.updateReview(reviewDTO);
        ReviewDTO result = null;

        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            log.debug("Call to updateReview failed: " + responseEntity.getStatusCode());
        } else {
            result = responseEntity.getBody();
        }
        return result;
    }

    public String deleteBasicReview(String uid) {
        ResponseEntity<String> responseEntity = integration.deleteReview(uid);
        String result = null;

        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            log.debug("Call to deleteReview failed: " + responseEntity.getStatusCode());
        } else {
            result = responseEntity.getBody();
        }
        return result;
    }

    public ReviewDTO getBasicReview(String uid) {
        ResponseEntity<ReviewDTO> responseEntity = integration.getReview(uid);
        ReviewDTO dto = null;

        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            log.debug("Call to getReview failed: " + responseEntity.getStatusCode());
        } else {
            dto = responseEntity.getBody();
        }
        return dto;
    }

    public List<ReviewDTO> getBasicAllReviewByUserUid(String userUid) {
        ResponseEntity<List<ReviewDTO>> responseEntity = integration.getAllReviewByUserUid(userUid);
        List<ReviewDTO> dto = null;

        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            log.debug("Call to getReview failed: " + responseEntity.getStatusCode());
        } else {
            dto = responseEntity.getBody();
        }
        return dto;
    }

    public List<ReviewDTO> getBasicAllReviewByProductUid(String productUid) {
        ResponseEntity<List<ReviewDTO>> responseEntity = integration.getAllReviewByProductUid(productUid);
        List<ReviewDTO> dto = null;

        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            log.debug("Call to getReview failed: " + responseEntity.getStatusCode());
        } else {
            dto = responseEntity.getBody();
        }
        return dto;
    }

    public List<ReviewDTO> getBasicReviewByUserUidAndProductUid(String userUid, String productUid) {
        ResponseEntity<List<ReviewDTO>> responseEntity = integration.getReviewByUserUidAndProductUid(userUid, productUid);
        List<ReviewDTO> dto = null;

        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            log.debug("Call to getReview failed: " + responseEntity.getStatusCode());
        } else {
            dto = responseEntity.getBody();
        }
        return dto;
    }
}
