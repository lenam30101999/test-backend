package com.aibles.reviewcompositeservice.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicConfig {
  public static final String REVIEW_TOPIC = "event.review.topic";

  @Value("${spring.kafka.topic.num-partitions}")
  private int numPartitions;

  @Value("${spring.kafka.topic.replication-factor}")
  private short replicationFactor;

  @Bean
  public NewTopic newTopic() {
    return new NewTopic(REVIEW_TOPIC, numPartitions, replicationFactor);
  }

}
