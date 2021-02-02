package com.aibles.categorycompositeservice.kafka.producer;

import com.aibles.categorycompositeservice.kafka.TopicConfig;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class CategoryProducer {

    @Autowired
    private KafkaTemplate<Long, String> categoryKafkaTemplate;

    public String send(String uid){
        categoryKafkaTemplate.send(TopicConfig.CATEGORY_TOPIC, uid);
        log.info("Send success!");
        return uid;
    }
}
