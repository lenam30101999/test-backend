package com.aibles.reviewcompositeservice.kafka.consumer;

import com.aibles.reviewcompositeservice.kafka.Constants;
import com.aibles.reviewcompositeservice.kafka.TopicConfig;
import com.aibles.reviewcompositeservice.util.ReviewBasic;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class ReviewConsumer {
    @Autowired
    private ReviewBasic reviewBasic;

    @KafkaListener(topics = TopicConfig.REVIEW_TOPIC)
    @Retryable(
            value = {
                    Exception.class
            },
            maxAttempts = Constants.MAX_ATTEMPTS,
            backoff = @Backoff(delay = Constants.DELAY_RETRY))
    public void process(String uid) {
        reviewBasic.deleteBasicReview(uid);
        log.info("Success");
    }
}
