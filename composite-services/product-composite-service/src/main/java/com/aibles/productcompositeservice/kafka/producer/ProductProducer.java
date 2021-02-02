package com.aibles.productcompositeservice.kafka.producer;

import com.aibles.productcompositeservice.kafka.TopicConfig;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ProductProducer {

    @Autowired
    private KafkaTemplate<Long, String> productKafkaTemplate;

    public String send(String uid){
        productKafkaTemplate.send(TopicConfig.PRODUCT_TOPIC, uid);
        log.info("Send success!");
        return uid;
    }
}
