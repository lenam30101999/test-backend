package com.aibles.categorycompositeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@EnableCircuitBreaker
@EnableDiscoveryClient
@EnableHystrixDashboard
@EnableAsync
@Configuration
@ComponentScan({"com.aibles.categorycompositeservice.service",
				"com.aibles.utils",
				"com.aibles.categorycompositeservice.util",
				"com.aibles.categorycompositeservice.kafka.producer"})
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableScheduling
@SpringBootApplication
public class CategoryCompositeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CategoryCompositeServiceApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
