package com.aibles.categorycompositeservice.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicConfig {
  public static final String CATEGORY_TOPIC = "event.category.topic";

  @Value("${spring.kafka.topic.num-partitions}")
  private int numPartitions;

  @Value("${spring.kafka.topic.replication-factor}")
  private short replicationFactor;

  @Bean
  public NewTopic newTopic() {
    return new NewTopic(CATEGORY_TOPIC, numPartitions, replicationFactor);
  }

}
