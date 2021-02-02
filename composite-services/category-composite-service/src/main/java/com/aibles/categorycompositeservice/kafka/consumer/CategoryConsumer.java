package com.aibles.categorycompositeservice.kafka.consumer;

import com.aibles.categorycompositeservice.kafka.Constants;
import com.aibles.categorycompositeservice.kafka.TopicConfig;
import com.aibles.categorycompositeservice.util.CategoryBasic;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class CategoryConsumer {
    @Autowired
    private CategoryBasic categoryBasic;

    @KafkaListener(topics = TopicConfig.CATEGORY_TOPIC)
    @Retryable(
            value = {
                    Exception.class
            },
            maxAttempts = Constants.MAX_ATTEMPTS,
            backoff = @Backoff(delay = Constants.DELAY_RETRY))
    public void process(String uid) {
        categoryBasic.deleteBasicCategory(uid);
        log.info("Success");
    }
}
