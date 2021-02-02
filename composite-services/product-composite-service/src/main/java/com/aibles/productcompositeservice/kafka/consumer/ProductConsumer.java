package com.aibles.productcompositeservice.kafka.consumer;

import com.aibles.productcompositeservice.kafka.Constants;
import com.aibles.productcompositeservice.kafka.TopicConfig;
import com.aibles.productcompositeservice.util.ProductBasic;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class ProductConsumer {
    @Autowired
    private ProductBasic productBasic;

    @KafkaListener(topics = TopicConfig.PRODUCT_TOPIC)
    @Retryable(
            value = {
                    Exception.class
            },
            maxAttempts = Constants.MAX_ATTEMPTS,
            backoff = @Backoff(delay = Constants.DELAY_RETRY))
    public void process(String uid) {
        productBasic.deleteBasicProduct(uid);
        log.info("Success");
    }
}
