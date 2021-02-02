package com.aibles.reviewcompositeservice.kafka.producer;

import com.aibles.reviewcompositeservice.kafka.TopicConfig;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ReviewProducer {

    @Autowired
    private KafkaTemplate<Long, String> reviewKafkaTemplate;

    public String send(String uid){
        reviewKafkaTemplate.send(TopicConfig.REVIEW_TOPIC, uid);
        log.info("Send success!");
        return uid;
    }
}
