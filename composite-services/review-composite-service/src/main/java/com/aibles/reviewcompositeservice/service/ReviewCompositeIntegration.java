package com.aibles.reviewcompositeservice.service;

import com.aibles.reviewcompositeservice.util.ReviewEndPoint;
import com.aibles.utils.util.ServiceUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import dtos.ReviewDTO;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
@Slf4j
public class ReviewCompositeIntegration {
    
    private RestOperations restTemplate;
    private final ServiceUtils util;
    private final ObjectMapper mapper;

    @Autowired
    public ReviewCompositeIntegration(RestOperations restTemplate, ServiceUtils util, ObjectMapper mapper) {
        this.restTemplate = restTemplate;
        this.util = util;
        this.mapper = mapper;
    }

    @HystrixCommand(fallbackMethod = "defaultCreateReview", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")})
    public ResponseEntity<ReviewDTO> createReview(ReviewDTO reviewDTO) {
        String url = ReviewEndPoint.URL_REVIEW_SERVICE;

        ResponseEntity<ReviewDTO> result = restTemplate.postForEntity(url, reviewDTO, ReviewDTO.class);

        ReviewDTO reviewDtoResult = result.getBody();
        return util.createOkResponse(reviewDtoResult);
    }

    /**
     * Fallback method for createReview()
     *
     * @return
     */
    public ResponseEntity<ReviewDTO> defaultCreateReview(ReviewDTO reviewDTO) {
        log.debug("Using fallback method for review with uid: " + reviewDTO.getUid());
        return new ResponseEntity<>(reviewDTO, HttpStatus.OK);
    }

    @HystrixCommand(fallbackMethod = "defaultUpdateReview", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")})
    public ResponseEntity<ReviewDTO> updateReview(ReviewDTO reviewDTO) {
        String url = ReviewEndPoint.URL_REVIEW_SERVICE;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ReviewDTO> requestUpdate = new HttpEntity<>(reviewDTO ,headers);
        ResponseEntity<ReviewDTO> result = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                requestUpdate,
                ReviewDTO.class);
        ReviewDTO reviewDtoResult = result.getBody();

        return util.createOkResponse(reviewDtoResult);
    }

    /**
     * Fallback method for updateReview()
     *
     * @return
     */
    public ResponseEntity<ReviewDTO> defaultUpdateReview(ReviewDTO reviewDTO) {
        log.debug("Using fallback method for review with uid: " + reviewDTO.getUid());
        ReviewDTO dto = new ReviewDTO();
        dto.setComment("ERROR");

        return util.createResponse(
                dto,
                HttpStatus.OK);
    }

    @HystrixCommand(fallbackMethod = "defaultDeleteReview", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")})
    public ResponseEntity<String> deleteReview(String uid) {
        String url = ReviewEndPoint.URL_REVIEW_SERVICE + uid;
        log.debug("deleteReview from URL: " + url);

        restTemplate.delete(url);
        return util.createOkResponse(HttpStatus.OK.getReasonPhrase());
    }

    /**
     * Fallback method for deleteReview()
     *
     * @return
     */
    public ResponseEntity<String> defaultDeleteReview(String uid) {
        log.debug("Using fallback method for review with uid: " + uid );
        return util.createResponse(
                "ERROR",
                HttpStatus.OK);
    }

    @HystrixCommand(fallbackMethod = "defaultGetReview", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")})
    public ResponseEntity<ReviewDTO> getReview(String uid) {
        String url = ReviewEndPoint.URL_REVIEW_SERVICE + uid;
        ResponseEntity<ReviewDTO> result = restTemplate.getForEntity(url, ReviewDTO.class);

        ReviewDTO reviewDtoResult = result.getBody();
        log.debug("getReview: " + reviewDtoResult.toString());

        return util.createOkResponse(reviewDtoResult);
    }

    /**
     * Fallback method for getReview()
     *
     * @return
     */
    public ResponseEntity<ReviewDTO> defaultGetReview(String uid) {
        log.debug("Using fallback method for review with uid = " + uid);
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setComment("ERROR");
        return util.createResponse(
                reviewDTO,
                HttpStatus.OK);
    }

    @SneakyThrows
    @HystrixCommand(fallbackMethod = "defaultGetAllReviewByUserUid", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")})
    public ResponseEntity<List<ReviewDTO>> getAllReviewByUserUid(String userUid) {
        log.debug("Will call getAllReviewByUser with hystrix protection");

        String url = ReviewEndPoint.URL_REVIEW_SERVICE + "?user_uid=" + userUid;
        log.debug("getAllReview from URL" + url);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<String> result = restTemplate.exchange(
                builder.build().encode().toUri(),
                HttpMethod.GET,
                entity,
                String.class);

        mapper.registerModule(new JavaTimeModule());
        List<ReviewDTO> reviewDtoResult = Arrays.asList(mapper.readValue(result.getBody(), ReviewDTO[].class));

        return util.createOkResponse(reviewDtoResult);
    }

    /**
     * Fallback method for getAllReview()
     *
     * @return
     */
    public ResponseEntity<List<ReviewDTO>> defaultGetAllReviewByUserUid(String userUid) {
        log.debug("Using fallback method for reviews with user = " + userUid);
        List<ReviewDTO> reviewDTOs = new ArrayList<>();
        return util.createResponse(
                reviewDTOs,
                HttpStatus.OK);
    }

    @SneakyThrows
    @HystrixCommand(fallbackMethod = "defaultGetAllReviewByProductUid", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")})
    public ResponseEntity<List<ReviewDTO>> getAllReviewByProductUid(String productUid) {
        log.debug("Will call getAllReviewByProduct with hystrix protection");

        String url = ReviewEndPoint.URL_REVIEW_SERVICE + "?product_uid=" + productUid;
        log.debug("getAllReview from URL" + url);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<String> result = restTemplate.exchange(
                builder.build().encode().toUri(),
                HttpMethod.GET,
                entity,
                String.class);

        mapper.registerModule(new JavaTimeModule());
        List<ReviewDTO> reviewDtoResult = Arrays.asList(mapper.readValue(result.getBody(), ReviewDTO[].class));

        return util.createOkResponse(reviewDtoResult);
    }

    /**
     * Fallback method for getAllReview()
     *
     * @return
     */
    public ResponseEntity<List<ReviewDTO>> defaultGetAllReviewByProductUid(String productUid) {
        log.debug("Using fallback method for reviews with product = " + productUid);
        List<ReviewDTO> reviewDTOs = new ArrayList<>();
        return util.createResponse(
                reviewDTOs,
                HttpStatus.OK);
    }

    @SneakyThrows
    @HystrixCommand(fallbackMethod = "defaultGetAllReviewByUserUidAndProductUid", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")})
    public ResponseEntity<List<ReviewDTO>> getReviewByUserUidAndProductUid(String userUid, String productUid) {
        log.debug("Will call getAllReviewByUserAndProduct with hystrix protection");

        String url = ReviewEndPoint.URL_REVIEW_SERVICE + "?user_uid=" + userUid + "?product_uid=" + productUid;
        log.debug("getAllReviewByUserAndProduct from URL" + url);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<String> result = restTemplate.exchange(
                builder.build().encode().toUri(),
                HttpMethod.GET,
                entity,
                String.class);

        mapper.registerModule(new JavaTimeModule());
        List<ReviewDTO> reviewDtoResult = Arrays.asList(mapper.readValue(result.getBody(), ReviewDTO[].class));

        return util.createOkResponse(reviewDtoResult);
    }

    /**
     * Fallback method for getAllReview()
     *
     * @return
     */
    public ResponseEntity<List<ReviewDTO>> defaultGetAllReviewByUserUidAndProductUid(String userUid, String productUid) {
        log.debug("Using fallback method for reviews with user = " + userUid + " and product = " + productUid);
        List<ReviewDTO> reviewDTOs = new ArrayList<>();
        return util.createResponse(
                reviewDTOs,
                HttpStatus.OK);
    }
}
